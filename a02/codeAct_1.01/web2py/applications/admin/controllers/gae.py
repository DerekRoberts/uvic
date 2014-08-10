### this works on linux only

import re
try:
    import fcntl
    import subprocess
    import signal
    import os
    import shutil
except:
    session.flash='sorry, only on Unix systems'
    redirect(URL(request.application,'default','site'))

forever=10**8

def kill():
    p = cache.ram('gae_upload',lambda:None,forever)
    if not p or p.poll()!=None:
        return 'oops'
    os.kill(p.pid, signal.SIGKILL)
    cache.ram('gae_upload',lambda:None,-1)

class EXISTS(object):
    def __init__(self, error_message='file not found'):
        self.error_message = error_message
    def __call__(self, value):
        if os.path.exists(value):
            return (value,None)
        return (value,self.error_message)

def deploy():
    regex = re.compile('^\w+$')
    apps = sorted(file for file in os.listdir(apath(r=request)) if regex.match(file))
    form = SQLFORM.factory(
        Field('appcfg',default=GAE_APPCFG,label='Path to appcfg.py',
              requires=EXISTS(error_message=T('file not found'))),
        Field('google_application_id',requires=IS_ALPHANUMERIC()),
        Field('applications',requires=IS_IN_SET(apps,multiple=True),
              label=T('web2py apps to deploy')),
        Field('email',requires=IS_EMAIL(),label=T('GAE Email')),
        Field('password',requires=IS_NOT_EMPTY(),label=T('GAE Password')))
    cmd = output = errors= ""
    if form.accepts(request,session):
        try:
            kill()
        except:
            pass
        ignore_apps = [item[1] for item in apps \
                           if not item[1] in request.vars.applications]
        regex = re.compile('\(applications/\(.*')
        yaml = apath('../app.yaml', r=request)
        if not os.path.exists(yaml):
            example = apath('../app.example.yaml', r=request)
            shutil.copyfile(example,yaml)            
        data=open(yaml,'r').read()
        data = re.sub('application:.*','application: %s' % form.vars.google_application_id,data)
        data = regex.sub('(applications/(%s)/.*)|' % '|'.join(ignore_apps),data)
        open(yaml,'w').write(data)

        path = request.env.applications_parent
        cmd = '%s --email=%s --passin update %s' % \
            (form.vars.appcfg, form.vars.email, path)
        p = cache.ram('gae_upload',
                      lambda s=subprocess,c=cmd:s.Popen(c, shell=True,
                                                        stdin=s.PIPE,
                                                        stdout=s.PIPE,
                                                        stderr=s.PIPE, close_fds=True),-1)
        p.stdin.write(form.vars.password+'\n')
        fcntl.fcntl(p.stdout.fileno(), fcntl.F_SETFL, os.O_NONBLOCK)
        fcntl.fcntl(p.stderr.fileno(), fcntl.F_SETFL, os.O_NONBLOCK)
    return dict(form=form,command=cmd)

def callback():
    p = cache.ram('gae_upload',lambda:None,forever)
    if not p or p.poll()!=None:
        return '<done/>'
    try:
        output = p.stdout.read()
    except:
        output=''
    try:
        errors = p.stderr.read()
    except:
        errors=''
    return (output+errors).replace('\n','<br/>')
