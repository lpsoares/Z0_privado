#!/bin/sh

python TestesHW/run.py -p3
python TestesSW/assembler.py
python TestesSW/emulate.py
python -m pytest -v TestesSW/teste.py 