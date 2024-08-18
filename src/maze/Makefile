all: clean install tests run

install:
	gradle build

uninstall:
	gradle clean

dvi:
	open ../../README_RUS.md

dist: uninstall
	gradle dist

tests: uninstall
	gradle test

run: clean install
	gradle run

clean:
	gradle clean
