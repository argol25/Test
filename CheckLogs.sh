#!/bin/bash

RPT="/home/arek/Dokumenty/Skrypty/LogsCheck/ResultFile/LogsSummary.txt"
DIRF="/home/arek/Dokumenty/Skrypty/LogsCheck/LogFilesToCheck.txt"
LOGS="/home/arek/Dokumenty/Skrypty/LogsCheck/Logs/"
WORDTC1="Lio"
WORDTC2="Ar"

cd $LOGS
ls *.txt > $DIRF 
> $RPT
while read LINE; do
	FTC="$LINE"
	echo "******************" >> $RPT
	echo "File : $FTC" >> $RPT 
	echo "" >> $RPT
		while read LINE; do
			if [[ $LINE = *$WORDTC1* ]] || [[ $LINE = *$WORDTC2* ]]; then
				echo "--------------------" >> $RPT
				echo "$LINE" >> $RPT
				echo "--------------------" >> $RPT
				echo "" >> $RPT
			fi
		done < $FTC
	echo "******************" >> $RPT
	echo "" >> $RPT
	echo "" >> $RPT
done < $DIRF

