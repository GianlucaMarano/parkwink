# Parkwink

Parkwink è un'applicazione di gestione dei parcheggi che gestisce un parcheggio automatizzato. I sistemi che lo
integrano possono: autenticarsi, gestire i posti auto e gestire i biglietti del parcheggio.
Questo README fornisce una panoramica dell'architettura del progetto e una descrizione dei componenti principali.

## Sommario

- [Alberatura](#alberatura-file)
- [Architettura](#architettura)
- [Componenti](#componenti)
- [Controller](#controller)
- [Servizi](#servizi)
- [Repository](#repository)
- [Entità](#entità)
- [DTO](#dto)
- [Sicurezza](#sicurezza)
- [Gestione delle Eccezioni](#gestione-delle-eccezioni)
- [Configurazione dell'ambiente di sviluppo](#configurazione-dellambiente-di-sviluppo)
- [Esecuzione dell'Applicazione](#esecuzione-dellapplicazione)
- [Testing](#testing)

## Alberatura file

Di seguito viene descritta la stuttura dei file presenti nel progetto

```

└── src/
    ├── main/
    │   ├── java/com/wink/parkwink/
    │   │   ├── api/                            Ogni sottocartella ha Controller e Service
    │   │   │   ├── auth/
    │   │   │   ├── lot/
    │   │   │   ├── ticket/
    │   │   │   └── user/
    │   │   ├── config/                         Configurazioni varie per Spring Boot
    │   │   │   ├── context/
    │   │   │   ├── customExceptions/
    │   │   │   ├── exceptionHandlers/
    │   │   │   ├── security/
    │   │   │   ├── InterceptorConfig.java
    │   │   │   └── ServiceInterceptor.java
    │   │   ├── entities/                       Entità Java e classi affini (DTO, Repository)
    │   │   │   ├── lot/
    │   │   │   ├── ticket/
    │   │   │   └── user/
    │   │   ├── utils/                          Wrapper per risposte JSON
    │   │   │   ├── jwt/
    │   │   │   └── response/
    │   │   └── ParkwinkApplication.java
    │   └── resources/
    │       ├── META-INF
    │       ├── static
    │       ├── templates
    │       ├── application.properties
    │       └── application-test.properties
    └── test/                                   I test sono divisi per argomento
        └── java/com/wink/parkwink
            ├── api/
            └── entities/

```

## Architettura

L'applicazione segue un'architettura a strati con i seguenti livelli chiave:

1. **Controller**: Gestisce le richieste e risposte HTTP.
2. **Servizio**: Contiene la logica di business.
3. **Repository**: Gestisce l'accesso ai dati e la persistenza.
4. **Entità**: Rappresenta il modello di dominio.
5. **DTO**: Oggetti di trasferimento dati per la comunicazione tra livelli.
6. **Sicurezza**: Gestisce l'autenticazione e l'autorizzazione.
7. **Gestione delle Eccezioni**: Gestione centralizzata delle eccezioni.

## Componenti

### Controller

I controller gestiscono le richieste HTTP e le mappano ai metodi appropriati dei servizi. Gestiscono anche la
validazione delle richieste e la formattazione delle risposte.

- **AuthController**: Gestisce la registrazione e l'autenticazione degli utenti.
- **LotController**: Gestisce i posti auto, inclusi creazione, aggiornamento, recupero ed eliminazione.
- **TicketController**: Gestisce i biglietti di parcheggio, inclusi creazione, aggiornamento, recupero ed eliminazione.
- **UserController**: Gestisce le informazioni degli utenti, inclusi creazione, aggiornamento, recupero ed eliminazione.

### Servizi

I servizi contengono la logica di business e interagiscono con le repository per eseguire operazioni CRUD.

- **AuthService**: Gestisce la registrazione e l'autenticazione degli utenti.
- **LotService**: Gestisce le operazioni sui posti auto.
- **TicketService**: Gestisce le operazioni sui biglietti di parcheggio.
- **UserService**: Gestisce le operazioni sugli utenti.

### Repository

I repository interagiscono con il database per eseguire operazioni CRUD.

- **UserRepository**: Gestisce i dati degli utenti.
- **LotRepository**: Gestisce i dati dei posti auto.
- **TicketRepository**: Gestisce i dati dei biglietti del parcheggio.

### Entità

Le entità rappresentano il modello di dominio e sono mappate alle tabelle del database.

- **User**: Rappresenta un utente nel sistema.
- **Lot**: Rappresenta un posto auto.
- **Ticket**: Rappresenta un biglietto di parcheggio.

### DTO

Gli Oggetti di Trasferimento Dati (DTO) vengono utilizzati per trasferire dati tra i livelli.

- **UserDTO**: Oggetto di trasferimento dati per le informazioni sugli utenti.
- **LotDTO**: Oggetto di trasferimento dati per le informazioni sui posti auto.
- **TicketDTO**: Oggetto di trasferimento dati per le informazioni sui biglietti di parcheggio.

### Sicurezza

Il livello di sicurezza gestisce l'autenticazione e l'autorizzazione.

- **JwtService**: Gestisce le operazioni sui JWT come la generazione e la validazione dei token.
- **JwtAuthenticationFilter**: Filtra e valida i token JWT nelle richieste in ingresso.
- **SecurityConfig**: Configura le impostazioni di sicurezza, inclusi i provider di autenticazione e le catene di
  filtri.
- **CustomAuthEntryPoint**: Gestisce le eccezioni di autenticazione.
- **CustomAccessDeniedHandler**: Gestisce le eccezioni di accesso negato.

### Gestione delle Eccezioni

Gestione centralizzata delle eccezioni per la gestione delle eccezioni specifiche dell'applicazione.

- **CustomAuthEntryPoint**: Gestisce gli errori di autenticazione.
- **CustomAccessDeniedHandler**: Gestisce gli errori di accesso negato.
- **FilterChainExceptionHandler**: Gestisce le eccezioni che si verificano nella catena di filtri.
- **ControllerExceptionHandler**: Gestisce le eccezioni generate dai controller.
- **Eccezioni Personalizzate**: Eccezioni personalizzate
  come `UserAlreadyExistsException`, `NoFreeLotAvailableException`, `ParkingNotEndedException`,
  e `PaymentExpiredException`.

## Configurazione dell'Ambiente di Sviluppo

Per configurare l'ambiente di sviluppo, segui questi passaggi:

1. **Installare Java Development Kit (JDK)**:
   Assicurati di avere installato JDK 17 o superiore.

2. **Installare Maven**:
   Assicurati di avere Maven installato. Puoi verificarlo eseguendo `mvn -v`.

3. **Installare Docker**:
   Docker è necessario per eseguire il database PostgreSQL.

4. **Impostare le Variabili d'Ambiente**:
   Configura le variabili d'ambiente necessarie, come la chiave segreta JWT e le credenziali del database, nel
   file `application.properties`.

## Esecuzione dell'Applicazione

Per eseguire l'applicazione, segui questi passaggi:

1. **Clona la repository**:
    ```sh
    git clone https://github.com/tuo-repo/parkwink.git
    cd parkwink
    ```

2. **Costruisci il progetto**:
    ```sh
    mvn clean package
    ```

3. **Esegui l'applicazione**:
    ```sh
    docker compose up
    ```

4. **Accedi all'applicazione**:

- L'applicazione sarà in esecuzione su `http://localhost:8080`.


5. **Terminare l'applicazione**:
   ```sh
   docker compose down 
   ```

## Testing

I test unitari e di integrazione sono forniti per garantire la correttezza dell'applicazione. Per eseguire i test, usa
il seguente comando:
I test unitari possono facilmente essere eseguiti lanciando

   ```sh
   mvn test
   ```

Per eseguire i test d'integrazione è necessario usare un db demo che è possibile avviare con il comando

```sh
docker run --name postgres-test -e POSTGRES_DB=testdb -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=testpassword -p 5432:5432 -d postgres
```

successivamente attivare i test d'integrazione dal pom.xml rimuovendo `<skipTests>true</skipTests>` e lanciare il
comando

```sh
mvn verify
```


