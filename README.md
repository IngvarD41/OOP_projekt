# Mp3 mängija
Sander Soodla, Ingvar Drikkit
***
## Kirjeldus
Projekti eesmärgiks oli luua lihtne tekstiridadena terminalis kuvatav mp3 failide mängija. Tahtsime luua programmi, mis
loeb kaustas olevate mp3 failide ID3 märgiseid ning võimaldab kasutajal laule programmi *playlist*-is sorteerida ning käivitada.
ID3 siltide lugemiseks kasutasime **mp3agic** teeki ning laulude käivitamiseks rakendasime **JavaFX**-i. Programmi kasutusjuhend 
on programmist kätte saadav.
***
## Klassid
### Song
Laulu klass laiendab mp3agic teegis olevat *MP3File* klassi, lisades sellele isendiväljad, kus hoitakse infot ID3 siltide 
kohta. Klassil on ka isendiväli *MediaPlayer*-i jaoks, et helifaili oleks selle välja kutsumisel lihtne käivitada. Peale 
konstruktori ja erinevate *get*-meetodite on selles klassis ka kolm *Comparator*-i, mis võimaldavad hiljem sorteerida 
selle klassi isendeid nii laulu pealkirja, esitaja kui ka albumi järgi.
### MusicCollection
Muusikakollektsiooni klass hoiab endas *Arraylist*-i, kuhu lähevad *Song* objektid. Meetod *setSongList* võtab argumendiks
kausta asukoha ning lisab kaustas olevad mp3 failid listi. Klassis on veel meetodid listi sorteerimiseks (*Song* klassis olevate *Comparator*-ite põhjal) ning muusikakollektsiooni tekstina kuvamiseks.
### MediaControl
Klass, milles toimub suhtlus kasutajaga ja mängija juhtimine.
Realiseerib *Runnable* liidest, pannakse tööle eraldi lõimes.
olulisem meetod:
*mediaControlWithTextInput()*
### MainApp
Siit algab programmi töö.
***
## Protsess ja panus
- **Sander** - JavaFX-i media module'i jaoks Gradle projekti üles seadmine, MainApp ja MediaControl klassid.\
Orienteeruv ajakulu: ~9 tundi 

- **Ingvar** - MusicCollection ja Song klasside kirjutamine, nimetatud klasside ja mp3agic teegi Gradle projekti integreerimine.\
  Orienteeruv ajakulu: ~6 tundi
***
## Mured
Kasutajalt sisendi saamiseks tuli teha eraldi lõim, 
sest Scanner sisendi ootamine pani programmi töö ja seega ka muusika seisma.
Lõimede (thread) kohta tuli natuke infot otsida.\
Kuna see projekt/programm kasutab juba algses staadiumis JavaFX-i ning veel ühte välist teeki, pidime kasutama enda 
seadistatud Gradle projekti. Gradle õppimine ja erinevate teekide tööle saamine võttis suhteliselt kaua aega.
***
## Hinnang
- **Sander** - Hästi: töötab.
- **Ingvar** - Projekti alguses seatud eesmärgid said täidetud, mis on positiivne. Kindlasti on programmi töös lünkasid, 
  mida tuleb parandada. Veahalduse, graafilise liidese ja muu sellisega tegelemegi projekti teises pooles.
***
## Testimine
Proovisime programmi tööd erinevate mp3 failidega. Hetkel on eelduseks, et kaustas on ainult mp3 failid ja failid on viisakalt 
sildistatud.
***
