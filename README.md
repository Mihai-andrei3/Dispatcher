# tema2 APD Ghita Mihai-Andrei 332CC

Implementare MyDispatcher: 
	*am adaugat la clasa din schelet inca o variabila ajutatoare "index" folosita pentru a calculca index-ul hostului catre care se va trimite task-ul in
politica Round Robin
	*implementare politici: am detaliat in comentarii detaliile implementarii
		-Round Robin: am folosit formula din cerinta : (i + 1)%n doar ca nu am mai adaugat + 1 pentru ca index ul meu incepe de la 0
		
		-Shortest Queue: am considerat coada minima cea din primul host si am stocat-o in variabila "shortest", iar index-ul pentru host-ul
	cu coada minima este stocat in "shortestIndex". Se parcurg toate host-urile si daca se gaseste unul cu coada mai mica se actualizeaza cele 2
	variabile cu valoarea host-ului "i".
	
		-SITA: daca tipul este short: task ul este adaugat in host-ul 0
		       daca tipul este medium: task ul este adaugat in host-ul 1
		       daca tipul este long: task ul este adaugat in host-ul 2
		    
		-LWL: se procedeaza asemanator cu ShortestQueue, unde acum cele 2 varibaile folosite sunt "minim_work" si "index"
		
Implementare MyHost: 

	*variabile adaugate: -tasks: coada cu prioritati thread-safe pentru a stoca task-urile. Pentru sortarea acestora in functie de prioritate 
am folosit un comparator, numit "Task_comparator"

			    -shutdown: flag cu valoarea initiala 0, care semnaleaza ca metoda shutdown a fost apelata, moment in care devine 1 si trebuie terminata executia.
			    
			    -CurrentTask: aici este stocat task-ul care ruleaza in momentul curent pe procesor
			    
	*metodele implementate:
		
		-run: am folosit o bucla infinita care se opreste atunci cand "shutdown" devine 1, in care se scoate din coada cate un task si este pus
	in "CurrentTask". Cat timp mai are o secunda de rulat, se apeleaza sleep cate o secunda si apoi aceasta este scazuta din timpul ramas. Daca apare
	un task in coada cu o prioritate mai mare iar task-ul curent este preemptibil, acesta este adaugat inapoi in coada iar task-ul curent devine noul
	task cu prioritate mai mare. Cand un task isi termina munca pe procesor se apeleaza metoda "finish" pe task-ul respectiv.
	
		-addTask: se adauga in coada task-ul primit ca si parametru
		
		-getQueueSize: intoarce numarul de elemente din coada plus 1, daca este si un task care ruleaza pe procesor
		
		-getWorkLeft: se aduna munca ramasa de la fiecare task din coada plus munca task-ului curent in variabila "workLeft", aceasta fiind returnata la final
		
		-shutdown: se marcheaza cu 1 flag-ul "shutdown", semnaland astfel oprirea host-ului
		
			    

	

