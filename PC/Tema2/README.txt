Vlad Theia Madalin 324CC - Tema 2 PC

Server:
Din moment de server-ul trebuie sa poata lucra cu orice numar de clienti UDP 
sau TCP, am folosit multiplexarea. Am deschis socketii UDP si TCP, am facut 
bind (si listen pentru TCP, permitant maxim 5 conexiuni in coada de asteptare).
Apoi am adaugat socketii in multimea de citire. Odata intrat in loop-ul infinit 
urmeaza multimplexarea proporiu-zisa (prin select) si trecerea prin multimea de 
socketi. Aici avem 4 optiuni:
- file descriptor-ul intrarii standard, ceea ce semnifica o comanda de la 
server (in acest caz, avem doar exit);
- socket-ul tcp, care inseamna o cerere de conectare din partea unui client 
TCP (pe care server-ul o accepta). Daca exista deja un client conectat cu 
acelasi ID, se trimite un flag si se inchide noul socket. Daca clientul a mai 
fost conectat, se reconecteaza si primeste mesajele ratate pentru topicurile 
la care avea SF=1. Altfel, se realizeaza o conexiune noua;
- socket-ul udp, care semnifica conexiune la un client udp, care trimite 
mesaje server-ului, urmand sa fie trimise mai departe clientilor tcp online 
abonati la topicul mesajului respectiv, sau puse pe coada de mesaje ratate a 
celor abonati cu SF=1 la acel topic;
- primirea unei comenzi din partea clientilor tcp. Daca mesajul este de lungime 
zero, clientul s-a deconectat, altfel clientul s-a abonat (reabonat, daca a mai 
fost abonat) sau dezabonat de la un topic.

Client TCP:
La fel ca in cazul server-ului, am folosit multimplexarea. Am deschis socket-ul 
pentru conexiunea la server, am facut connect si am trimis ID-ul pentru a vedea 
daca mai este cineva logat cu acest ID. Am primit un flag in functie de situatia 
respectiva (F - ID deja folosit de un client online, T - ID liber, R - 
reconectare la un ID deja folosit, dar fara client conectat). In ultimul caz, 
am verificat daca aveam mesaje cat am fost offline (pentru clientii cu SF=1 la 
anumite topic-uri) folosind flag-urile "none" (in cazul unei cozi goale de 
mesaje) sau "done" (primind si parsand mesajele ratate pana la golirea cozii). 
Dupa intrarea in loop-ul infinit are loc multimplexarea si parcurgerea multimii 
de socketi. Aflati aici, avem 2 cazuri:
- file descriptor-ul intrarii standard, care inseamna o comanda trimisa de la 
client catre server;
- mesaj primit de la server, care urmeaza sa fie parsat si afisat.
Parsarea mesajelor se face in 4 moduri diferite, in functie de tip-ul de date 
al mesajului.

Pentru o mai buna gestionare a datelor, am folosit doua clase: Client si Topic. 
Client are ca membrii socket-ul de pe care a fost pornit, ID-ul cu care s-a 
logat si statusul (pentru ca server-ul tine un vector de clienti, iar daca un 
client s-a deconectat nu este sters, doar este schimbat flag-ul status la 0, 
astfel putem deosebi o noua conexiune, ID-ul ne mai fiind prezent in vector, 
de o reconectare, in care ID-ul este deja acolo, doar ca actualizam scoket-ul), 
un vector de topic-uri la care este abonat si o coada de mesaje ratate. 
Topic are ca membrii numele (pentru a putea trece prin vectorul de topic-uri la 
care este abonat un client si sa comparam numele cu cel din mesajul primit de 
la un client udp), flag-ul SF (pentru a salva sau nu mesajele in coada) si un 
flag status (pentru a stii daca este prima abonare la acel topic sau o reabonare).

Tehnicile de programare defensiva folosite sunt: verificare pornirii cu un numar 
corect de argumente al server-ului/clientului, primirea input-ului corect si 
alertarea in caz contrat (doar la client) si trimiterea/primirea corecta de mesaje.