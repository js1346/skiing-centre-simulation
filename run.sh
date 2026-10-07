#!/bin/bash

rm -fr "maps"

javac -d bin $(find src -name "*.java")

java -ea -cp bin skiingCentre.Simulation "maps" < daneWejsciowe.txt

mv "maps" "tempMaps"

TEX_IN_DIR="tempMaps"
PDF_OUT_DIR="maps"     


mkdir -p "$PDF_OUT_DIR"

echo "Start of compiling  LaTeX files..."

for tex_file in "$TEX_IN_DIR"/*.tex; do

    if [ -f "$tex_file" ]; then
        echo "COmpiling: $tex_file"
        pdflatex -interaction=batchmode -output-directory="$PDF_OUT_DIR" "$tex_file"
    fi
done
rm -fr "tempMaps"
rm -f "$PDF_OUT_DIR"/*.log "$PDF_OUT_DIR"/*.aux
echo "Compilation finished."
echo "----------------------------------------"
