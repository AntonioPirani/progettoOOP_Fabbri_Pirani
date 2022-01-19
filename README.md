<p align="center">
  <img src="https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani/blob/main/WeatherApp/src/main/resources/static/icon.png" alt="Logo" style="width:90px;height:90px; margin-bottom:-10px;"/>
</p>
<br>

# <p align=center> **WeatherApp TempReader** </p>

Il seguente programma consente di visionare i dati relativi alle temperature di una città presa in ingresso. Tra le operazioni consentite si potrà confrontare tali dati e analizzarne le statistiche, a cui sarà possibile applicare dei filtri.

## **Indice**
- **[Descrizione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#descrizione)**
- **[Installazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#installazione)**
- **[Configurazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#configurazione)**
- **[Funzionalità](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#funzionalit%C3%A0)**
- **[Rotte](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#rotte)**
    - **[Statistiche e Filtri](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#statistiche-e-filtri)**
- **[Eccezioni](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#eccezioni)**
- **[Test](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#test)**
- **[Documentazione](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#documentazione)**
- **[Struttura del progetto](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#struttura-progetto)**
- **[Note](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#note)**
- **[Autori](https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani#authors)**

## **Descrizione**

L'applicativo è basato su dati che vengono forniti mediante chiamate a [OpenWeather](https://openweathermap.org/), tramite l’utilizzo di **API** gratuite. Grazie a queste ultime sarà possibile visionare e confrontare dati relativi alle temperature reali e percepite, di una specifica città fornita dall’utente. I dati restituiti posso essere di due tipi:
- **Attuali**: i quali verranno salvati in un file di testo in formato JSON, andando così a formare lo storico;
- **Passati**: riferiti a un periodo di tempo passato indicato dall'utente stesso, a seconda dell’opzione selezionata.

## **Installazione**

**WeatherApp TempReader** è installabile dal Prompt dei Comandi digitando:

```bash
git clone https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani
```
E' necessario settare la propria `API KEY` fornita da OpenWeather all'interno di `service/ServiceImplem.java`
```bash
private final String apiKey = "********************************";
```

## **Funzionalità**

Il programma è scritto interamente in linguaggio **Java** versione 11. Esso funziona mediante chiamate a delle **API** esterne. Per fare ciò è stata resa disponibile l'interfaccia `Service`, che contiene i metodi adibiti alle chiamate riguardanti il meteo passato e presente. Questa interfaccia viene utilizzata all'interno di `TempController`, il quale è la classe che gestisce le chiamate effettuate dall'utente, tramite l'utilizzo di rotte specifiche. Inoltre, grazie a questo controller, è possibile interfacciarsi alle statistiche, le quali utilizzano metodi presenti nell’interfaccia `StatsInterface`. Esse restituiscono i dati relativi ai valori massimi, minimi, di media e di varianza di temperature reali e percepite, prelevati dallo storico. E’ inoltre possibile applicare un filtro (orario, giornaliero o settimanale) per scindere le informazioni restituite.

## **Rotte**

|**N**| **Tipo** | **Rotta**     | **Descrizione**|
| -------- | -------- | ------ | ------------------------- |
|**1**|`GET` | `/current` |Rotta di tipo **GET** per ottenere la temperatura reale e percepita di una città, e salvare le informazione in uno storico con cadenza oraria.|
|**2**|`GET` | `/compare` |Rotta di tipo **GET** per confrontare le temperature reali e percepite, in un dato range temporale espresso in giorni.|
|**3**| `GET` | `/statistics`| Rotta di tipo **GET** per restituire le statistiche delle temperature di una città, alle quali si potrà eventualmente applicare un filtro.|
|**4**|`GET` | `/error`| Rotta di tipo **GET** che restituisce un JSON contenente i messaggi di errore per quando la rotta specificata non è consentita.|

### **1-** `/current`
Questa **rotta** permette di ottenere la temperatura reale e percepita corrente di una determinata città. Questi dati verranno utilizzati per la creazione di uno storico su base oraria. Nel caso in cui non fosse passata almeno 1 ora dall'ultima chiamata i dati non verranno salvati. Per realizzare tutto ciò è necessario effettuare una chiamata indiretta alla **API** di geolocalizzazione delle coordinate della città. La città di default è:
  - `cityName` = Ancona.
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
Questa **rotta** ha la funzione di confrontare le temperature effettive e percepite attuali di una città con quelle relative ad un dato range temporale passato. Questo periodo di tempo è riferito al numero di giorni antecedenti alla chiamata effettuata, inseriti dall'utente nel parametro `previousDay` che deve essere compreso tra 1 e 5. I parametri di default sono:
  - `cityName` = Ancona;
  - `previousDay` = 1;
```bash
{
    "temp": {
        "result": "La temperatura reale rispetto alla media di 1 giorno fa è diminuita di 0.46 gradi"
    },
    "cityName": "Ancona",
    "feels_like": {
        "result": "La temperatura percepita rispetto alla media di 1 giorno fa è aumentata di 0.57 gradi"
    }
}
```

### **3-** `/statistics`

Questa **rotta** ha il compito di restituire le statistiche di una città prelevate dallo storico creato con la chiamata `/current`. Tali statistiche restituiscono i valori massimi, minimi, di media e di varianza delle temperature reali e percepite. E' possibille applicare dei filtri di tipo orario, giornaliero e settimanale, con la istanza di tempo specificata dall'utente. Se non viene applicato nessun filtro, lo storico verrà analizzato per intero. I parametri:
  - `cityName` = Ancona (di default)
  - `filterBy` = hour, day e week (non richiesto)
  - `time` >= 1 (non richiesto)

### **4-** `/error`
Questa **rotta** permette di restituire una pagina HTML personalizzata come messaggio di errore, quando la rotta specificata non è consentita.
```bash
{
  "readMe":"https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani",
  "error":"Il comando inserito non è stato riconosciuto",
  "errorPage":"https://htmlpreview.github.io/?https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani/blob/main/WeatherApp/src/main/resources/static/errorPage.html"
}
```

### **Statistiche e Filtri**

Di seguito si specifica l'output delle statistiche con e senza filtro, in formato JSON:

  Senza filtro `localhost:8080/statistics?cityName=Ancona`
  
  ```bash
{
    {
      "temp": {
          "min": 4.03,
          "avg": 6.628,
          "max": 5.95,
          "var": 6.958
      },
      "cityName": "Ancona",
      "feels_like": {
          "min": 5.12,
          "avg": 7.076,
          "max": 5.95,
          "var": 4.885
      }
    }
}
```
  
  Con filtro `localhost:8080/statistics?cityName=Ancona&filterBy=day&time=1`
  
   ```bash
  {
     {
      "temp": {
          "filter": "day",
          "timeAgo": 1,
          "min": 4.03,
          "avg": 6.413,
          "max": 5.95,
          "var": 2.989
      },
      "cityName": "Ancona",
      "feels_like": {
          "filter": "day",
          "timeAgo": 1,
          "min": 5.12,
          "avg": 6.808,
          "max": 5.95,
          "var": 2.487
       }
     }
  }
```
  
## **Eccezioni**

|**N**|**Nome**     | **Descrizione**|
| -------- |------- | ------------------------- |
|**1**|`CityNotFoundException`| Classe per la gestione dell'eccezione di inserimento di una città non valida. |
|**2**|`HistoryException`| Classe per la gestione dell'eccezione del caso in cui lo storico richiesto per le statistiche non esiste. |
|**3**|`HourException`| Classe per la gestione dell'eccezione di un intervallo orario non valido.  |
|**4**|`InvalidPeriodException`| Classe per la gestione dell'eccezione di un inserimento del periodo preso in considerazione non valido. |

### **1-** `CityNotFoundException`
Questa **eccezione** viene generata qualora l'inserimento di una città non è valida.
```bash
  {
      "dateTime":1642434506,
      "feelsLike":23.45,
      "temp":23.68,
      "cityName":"Ancon",
      "timeZone":3600,
      "lon":-77.11165474769231,
      "cityId":6542126,
      "lat":-11.69655375
  }
```

### **2-** `HistoryException`
Questa **eccezione** viene eseguita qualora lo storico richiesto per le statistiche non esistesse.
```bash
  Storico di Ancona vuoto
```

### **3-** `HourException`
Questa **eccezione** viene generata qualora un intervallo orario non è valido.
```bash
  Il filtro inserito non è corretto

  Le opzioni sono hour, day, week
```

### **4-** `InvalidPeriodException`
Questa **eccezione** viene eseguita qualora l'inserimento del periodo preso in considerazione non è valido.
```bash
  Non è stato inserito un periodo di tempo valido
```

## **Test**

|**Nome**     | **Descrizione**|
| ------- | ------------------------- |
|`TestController`| Classe per la gestione del test dei metodi della classe `TempController`.|
|`TestCity`| Classe per la gestione del test dei metodi della classe `City`.|
|`TestService`| Classe per la gestione del test dei metodi della classe `Service`. |
|`TestStatistics`| Classe per la gestione del test dei metodi della classe `Statistics`.|

### `TestController`
Questa classe di **test** serve per verificare il corretto funzionamento dei metodi della classe `TempController`.
Questo è un esempio di test per il metodo `GetTemperature()`:
```bash
@Autowired
private MockMvc mockMvc;
  
    @Test
    public void testGetTemperature() throws Exception
    {
      this.mockMvc.perform(get("/current")).andExpect(status().isOk());
    }
```

### `TestCity`
Questa classe di **test** serve per verificare il corretto funzionamento dei metodi della classe `City`.
```bash
    @Test
      public void testJSON()
      {
        assertEquals(city.toJson().toString(),json);
      }
```


## **Documentazione**

Di seguito forniremo la **documentazione** `JavaDoc` dell'applicativo

- [JavaDoc](https://linktodocumentation)

## **Struttura progetto**

## **Note**

Il programma è stato realizzato tramite l'utilizzo di una `API KEY` gratuita. Proprio per questo il progetto è sottoposto ad alcune limitazioni, tra cui:
- la durata limitata della chiave;
- il numero limitato di chiamate orarie disponibili;
- lo storico relativo a una città di massimo 5 giorni antecedenti alla chiamata.

Come è possibile notare, vi è una differenza tra il JSON restituito dalla chiamata /current e quello salvato sullo storico. In quest'ultimo infatti si è deciso di non salvare le informazioni non utili, tra cui l'id della città e la timezone, al fine di salvare spazio.

## **Authors**

- [Antonio Pirani ](https://github.com/AntonioPirani)
- [Matteo Fabbri](https://github.com/MatteoFabbri136)




