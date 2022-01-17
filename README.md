<p align="center">
  <img src="https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani/blob/main/WeatherApp/src/main/resources/static/icon.png" alt="Logo" style="width:100px;height:100px;"/>
</p>
<br>

# <p align=center> **WeatherApp TempReader** </p>

<p style="text-align: justify;">Il seguente programma consente di visionare i dati relativi alle temperature di una città presa in ingresso. Tra le operazioni consentite si potrà confrontare tali dati e analizzarne le statistiche, a cui sarà possibile applicare dei filtri.</p>

## **Indice**
- **[Descrizione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#descrizione)**
- **[Installazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#installazione)**
- **[Configurazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#configurazione)**
- **[Funzionalità](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#funzionalit%C3%A0)**
- **[Rotte](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#rotte)**
    - **[Statistiche e Filtri](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#statistiche)**
- **[Eccezioni](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#eccezioni)**
- **[Test](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#test)**
- **[Documentazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#documentazione)**
- **[Struttura del progetto](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#struttura-progetto)**
- **[Note](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#note)**
- **[Autori](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#authors)**

## **Descrizione**

<p style="text-align: justify;">L'applicativo è basato su dati che vengono forniti mediante chiamate a [OpenWeather](https://openweathermap.org/), tramite l’utilizzo di **API** gratuite. Grazie a queste ultime sarà possibile visionare e confrontare dati relativi alle temperature reali e percepite, di una specifica città fornita dall’utente. I dati restituiti posso essere di due tipi:
- Attuali: i quali verranno salvati in un file di testo in formato JSON, andando così a formare lo storico;
- Passati: riferiti a un periodo di tempo passato indicato dall'utente stesso, a seconda dell’opzione selezionata.</p>

## **Installazione**

progettoOOP_Fabbri_Pirani è installabile dal Prompt dei Comandi digitando:

```bash
git clone https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani
```
E' necessario settare la propria `API KEY` fornita da OpenWeather all'interno di `service/ServiceImplem.java`
```bash
private final String apiKey = "********************************";
```

## **Funzionalità**

<p style="text-align: justify;">Il programma è scritto interamente in linguaggio Java versione 11. Esso funziona mediante chiamate a delle API esterne. Per fare ciò è stata resa disponibile l'interfaccia `Service`, che contiene i metodi adibiti alle chiamate riguardanti il meteo passato e presente. Questa interfaccia viene utilizzata all'interno di `TempController`, il quale è la classe che gestisce le chiamate effettuate dall'utente, tramite l'utilizzo di rotte specifiche. Inoltre, grazie a questo controller, è possibile interfacciarsi alle statistiche, le quali utilizzano metodi presenti nell’interfaccia `StatsInterface`. Esse restituiscono i dati relativi ai valori massimi, minimi, di media e di varianza di temperature reali e percepite, prelevati dallo storico. E’ inoltre possibile applicare un filtro (orario, giornaliero o settimanale) per scindere le informazioni restituite.</p>

## **Rotte**

|**N**| **Tipo** | **Rotta**     | **Descrizione**|
| -------- | -------- | ------ | ------------------------- |
|**1**|`GET` | `/current` |Rotta di tipo GET per ottenere la temperatura corrente di una città, con il metodo saveEveryHour che ci permette di salvarle con cadenta oraria.|
|**2**|`GET` | `/compare` |Rotta di tipo GET per confrontare le temperature correnti e percepite, in dato range temporale.|
|**3**| `GET` | `/statistics`| Rotta di tipo GET per restituire il filtraggio delle statistiche in base alla periodicità: giorni, fascia oraria, settimanale.|
|**4**|`GET` | `/error`| Rotta che restituisce una pagina HTML personalizzata come messaggio di errore, quando la rotta specificata non è consentita.|

### **1-** `/current`
<p style="text-align: justify;">Questa rotta permette di ottenere la temperatura corrente di una determinata città, in particolare il metodo `saveEveryHour` ha la funzione di salvare queste temperature con cadenta oraria in un file di testo, che costituirà lo storico lo storico.</p>
```bash
    {
        "dateTime":1642357460,
        "feelsLike":6.39,
        "temp":4.93,
        "cityName":"Ancona",
        "timeZone":3600,
        "lon":13.5170982,
        "cityId":3183087,
        "lat":43.6170137
    }
```

### **2-** `/compare`
<p style="text-align: justify;">Questa rotta ha la funzione di confrontare lo storico sulle temperature effettive e percepite di una città in dato range temporale, definito con l'attributo `previousDay` che deve essere compreso tra 1 e 5.</p>
```bash
    Confronto Temperature - **Ancona**:

    La temperatura reale rispetto alla media di 1 giorno fa è diminuita di **2.047** gradi.

    La temperatura percepita rispetto alla media di 1 giorno fa è diminuita di **0.796** gradi.
```

### **3-** `/statistics`
<p style="text-align: justify;">Questa rotta ha il compito di restituire il filtraggio delle statistiche di una città dallo storico, in base alla periodicità (giorni, fascia oraria, settimanale), tutto questo attraverso l'attributo `filterBy` e l'attributo `time` con cui si specifica l'entita della retroazione.</p>
```bash
```

### **4-** `/error`
<p style="text-align: justify;">Questa rotta permette di restituire una pgina HTML personalizzata come messaggio di errore, quando la rotta specificata non è consentita.</p>
```bash
```

## **Statistiche e Filtri**

## **Eccezioni**

|**N**|**Nome**     | **Descrizione**|
| -------- |------- | ------------------------- |
|**1**|`CityNotFoundException`| Classe per la gestione dell'eccezione di inserimento di una città non valida. |
|**2**|`HistoryException`| Classe per la gestione dell'eccezione... |
|**3**|`HourException`| Classe per la gestione dell'eccezione di un intervallo orario non valido.  |
|**4**|`InvalidPeriodException`| Classe per la gestione dell'eccezione di un inserimento del periodo preso in considerazione non valido. |

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
| -------- |------- | ------------------------- |
|**1**|`TestController`| Classe per la gestione del test dei metodi della classe `TempController`.|
|**2**|`TestCity`| Classe per la gestione del test dei metodi della classe `City`.|
|**3**|`TestService`| Classe per la gestione del test dei metodi della classe `Service`. |
|**4**|`TestStatistics`| Classe per la gestione del test dei metodi della classe `Statistics`.|

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

## **Note**

<p style="text-align: justify;">Il programma è stato realizzato tramite l'utilizzo di una `API KEY` gratuita. Proprio per questo il progetto è sottoposto ad alcune limitazioni, tra cui:
- la durata limitata della chiave;
- il numero limitato di chiamate orarie disponibili;
- lo storico relativo a una città di massimo 5 giorni antecedenti alla chiamata.</p>

## **Authors**

- [@Antonio Pirani ](https://github.com/AntonioPirani)
- [@Matteo Fabbri](https://github.com/MatteoFabbri136)




