clear;
python bin/generate.py generate/bubble_sort/bubble_sort_min_position.py code_library;
python bin/generate.py generate/bubble_sort/bubble_sort_swap_count.py code_library;
python bin/generate.py generate/bubble_sort/bubble_sort_bug.py code_library;
python bin/generate.py generate/insertion_sort/insertion_sort_min_position.py code_library;
python bin/generate.py generate/insertion_sort/insertion_sort_swap_count.py code_library;
python bin/generate.py generate/insertion_sort/insertion_sort_bug.py code_library;
python bin/generate.py generate/selection_sort/selection_sort_min_position.py code_library;
python bin/generate.py generate/selection_sort/selection_sort_swap_count.py code_library;
python bin/generate.py generate/selection_sort/selection_sort_bug.py code_library;

firefox http://localhost:8080/codeAct/ca_controller/quiz.html?quiz_spec=../quiz_specs/sort_quiz_spec;

cd web2py;
./start.sh;

cd ..

