#   **Progetto Programmazione a Oggetti**

Il seguente programma consente di visionare e confrontare dati relativi alle temperature reali e percepite, di una città presa in ingresso. Inoltre questi ultimi potranno essere filtrate in base alla periodicità: giornaliera, settimanale e per fascia oraria.

## **Indice**
- **[Descrizione](https://linktodocumentation)**
- **[Installazione](https://linktodocumentation)**
- **[Configurazione](https://linktodocumentation)**
- **[Funzionalità](https://linktodocumentation)**
- **[Rotte](https://linktodocumentation)**
    - **[Statistiche](https://linktodocumentation)**
- **[Filtri](https://linktodocumentation)**
- **[Eccezioni](https://linktodocumentation)**
- **[Test](https://linktodocumentation)**
- **[Documentazione](https://linktodocumentation)**
- **[Struttura del progetto](https://linktodocumentation)**
- **[Autori](https://linktodocumentation)**

## **Descrizione**

Il seguente programma consente di visionare e confrontare dati relativi alle temperature reali e percepite, di una città presa in ingresso. I dati presi posso essere di due tipi:
- Attuali: i quali verranno salvati in un file di testo, consentendone quindi la crezione di uno storico;
- Passati: riferiti a un periodo di tempo passato indicato dall'utente stesso.
Inoltre potranno essere filtrati in base alla periodicità: giornaliera, settimanale e per fascia oraria.

## **Installazione**

progettoOOP_Fabbri_Pirani è installabile dal Prompt dei Comandi digitando:

```bash
git clone https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani
```

## **Configurazione**

L'applicativo è basato su dati che vengono forniti mediante chiamate a [OpenWeather](https://openweathermap.org/). Tutto questo è stato possibile grazie all'utilizzo di una `API KEY`, fornita dal sito citato sopra, in maniera gratuita. Proprio per questo il progetto è sottoposto ad alcune limitazioni, tra cui:
- la durata limitata della chiave;
- il numero limitato di chiamate orarie disponibili;
- lo storico relativo a una città di massimo 5 giorni antecedenti alla chiamata.

## **Funzionalità**

Il programma è basato sulle chiamate a delle API esterne. Per fare ciò è stata resa disponibile l'interfaccia `Service`, che contiene i metodi adibiti alle chiamate riguardanti il meteo passato e presente. Questa interfaccia viene utilizzata al'interno di `TempController`, la quale è la classe che gestisce le chiamate effettuate dall'utente, tramite l'utilizzo di rotte specifiche. Inoltre, grazie a questo controller, è possibile interfacciarsi alle statistiche, le quali vengono gestite da `StatsInterface`. Queste ultime ottengono i dati relativi ai valori massimi, minimi, di media e di varianza di temperature reali e percepite, prelevati dallo storico.

## **Rotte**

|**N**| **Tipo** | **Rotta**     | **Descrizione**|
| :-------- | | :-------- | :------- | :------------------------- |
|**1**|`GET` | `/current?cityName=Ancona` |Rotta di tipo GET per ottenere la temperatura corrente di una città, con il metodo saveEveryHour che ci permette di salvarle con cadenta oraria.|
|**2**|`GET` | `/compare?cityName=Ancona&prevDay=1` |Rotta di tipo GET per confrontare le temperature correnti e percepite, in dato range temporale.|
|**3**|| `GET` | `/statistics`| Rotta di tipo GET per restituire il filtraggio delle statistiche in base alla periodicità: giorni, fascia oraria, settimanale.|
|**4**||`GET` | `/error`| Rotta che restituisce una pgina HTML personalizzata come messaggio di errore, quando la rotta specificata non è consentita.|

### **1-** `/current?cityName=Ancona`
Questa rotta permette di ottenere la temperatura corrente di una determinata città, in particolare il metodo `saveEveryHour` ha la funzione di salvare queste temperature con cadenta oraria in un file di testo, che costituirà lo storico lo storico.
```bash
```

### **2-** `/compare?cityName=Ancona&prevDay=1`
Questa rotta ha la funzione di confrontare lo storico sulle temperature effettive e percepite di una città in dato range temporale, definito con l'attributo `previousDay` che deve essere compreso tra 1 e 5.
```bash
```

### **3-** `/statistics`
Questa rotta ha il compito di restituire il filtraggio delle statistiche di una città dallo storico, in base alla periodicità (giorni, fascia oraria, settimanale), tutto questo attraverso l'attributo `filterBy` e l'attributo `time` con cui si specifica l'entita della retroazione.
```bash
```

### **4-** `/error`
Questa rotta permette di restituire una pgina HTML personalizzata come messaggio di errore, quando la rotta specificata non è consentita.
```bash
```

## **Statistiche**

## **Eccezioni**

|**N**|**Nome**     | **Descrizione**|
| :-------- | |:------- | :------------------------- |
|**1**||`CityNotFoundException`| Classe per la gestione dell'eccezione di inserimento di una città non valida. |
|**2**||`HistoryException`| Classe per la gestione dell'eccezione... |
|**3**||`HourException`| Classe per la gestione dell'eccezione di un intervallo orario non valido.  |
|**4**||`InvalidPeriodException`| Classe per la gestione dell'eccezione di un inserimento del periodo preso in considerazione non valido. |

### **1-** `CityNotFoundException`
Questa eccezione viene generata qualora l'inserimento di una città non è valida.
```bash
```

### **2-** `HistoryException`
Questa eccezione viene eseguita qualora.
```bash
```

### **3-** `HourException`
Questa eccezione viene generata qualora un intervallo orario non è valido.
```bash
```

### **4-** `InvalidPeriodException`
Questa eccezione viene eseguita qualora l'inserimento del periodo preso in considerazione non è valido.
```bash
```

## **Test**

|**N**|**Nome**     | **Descrizione**|
| :-------- | |:------- | :------------------------- |
|**1**||`TestController`| Classe per la gestione del test dei metodi della classe `TempController`.|
|**2**||`TestCity`| Classe per la gestione del test dei metodi della classe `City`.|
|**3**||`TestService`| Classe per la gestione del test dei metodi della classe `Service`. |
|**4**||`TestStatistics`| Classe per la gestione del test dei metodi della classe `Statistics`.|

### **1-** `TestController`
Questa classe di test serve per verificare il corretto funzionamento dei metodi della classe `TempController`.
```bash
```

### **2-** `HistorTestCityyException`
Questa classe di test serve per verificare il corretto funzionamento dei metodi della classe `City`.
```bash
```

### **3-** `TestService`
Questa classe di test serve per verificare il corretto funzionamento dei metodi della classe `Service`.
```bash
```

### **4-** `TestStatistics`
Questa classe di test serve per verificare il corretto funzionamento dei metodi della classe `Statistics`.
```bash
```

## **Documentazione**

Di seguito forniremo la documentazione JavaDoc dell'applicativo

- [JavaDoc](https://linktodocumentation)

## **Struttura progetto**

## **Authors**

- [@Antonio Pirani ](https://www.github.com/octokatherine)
- [@Matteo Fabbri](https://www.github.com/octokatherine)




