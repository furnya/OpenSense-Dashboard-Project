package com.opensense.dashboard.client.utils;

import java.util.Date;

/**
 * The languages are set to german by default to change it use the setter
 *
 * @author Roeber
 */
public class Languages {

	/**
	 * German or english
	 */
	private static boolean de = true;
	private static boolean en = false;
	private static boolean es = false;

	public static final String GERMAN = "de";
	public static final String ENGLISH = "en";
	public static final String SPANISH = "es";

	private Languages() {
		// Empty private constructor to hide the implicit public one.
	}

	public static boolean isGerman() {
		return de;
	}

	public static boolean isEnglish() {
		return en;
	}

	public static boolean isSpanish() {
		return es;
	}

	public static void setGerman() {
		de = true;
		en = false;
		es = false;
	}

	public static void setEnglish() {
		de = false;
		en = true;
		es = false;
	}

	public static void setSpanish() {
		de = false;
		en = false;
		es = true;
	}

	public static String getActualLanguageString() {
		if (de) {
			return GERMAN;
		} else if (en) {
			return ENGLISH;
		} else {
			return SPANISH;
		}
	}

	public static String text() {
		if (de) {
			return "Das ist der text";
		} else if (en) {
			return "";
		} else {
			return "";
		}
	}

	public static String home() {
		if (de) {
			return "Zuhause";
		} else if (en) {
			return "Home";
		} else {
			return "Página principal";
		}
	}

	public static String search() {
		if (de) {
			return "Suche";
		} else if (en) {
			return "Search";
		} else {
			return "B\u00FAsqueda";
		}
	}

	public static String map() {
		if (de) {
			return "Karte";
		} else if (en) {
			return "Map";
		} else {
			return "Carta";
		}
	}

	public static String graphics() {
		if (de) {
			return "Grafiken";
		} else if (en) {
			return "Visualizations";
		} else {
			return "Gr\u00E1ficas";
		}
	}

	public static String list() {
		if (de) {
			return "Listen";
		} else if (en) {
			return "Lists";
		} else {
			return "Listas";
		}
	}

	public static String user() {
		if (de) {
			return "Benutzerverwaltung";
		} else if (en) {
			return "User";
		} else {
			return "gesti\u00F3n de usuarios";
		}
	}

	public static String errorDataPanelPageLoading() {
		if (de) {
			return "Fehler beim Laden der Seite";
		} else if (en) {
			return "Error while loading the page";
		} else {
			return "Error al cargar la p\u00E1gina";
		}
	}

	public static String searchPlace() {
		if (de) {
			return "Sensoren Ortsabhängig suchen";
		} else if (en) {
			return "Search by place";
		} else {
			return "Busca sensores basados \u200B\u200Ben la localizaci\u00F3n ";
		}
	}

	public static String maxSensors() {
		if (de) {
			return "Maximale Anzahl von Sensoren";
		} else if (en) {
			return "Maximum amount of sensors";
		} else {
			return "N\u00FAmero m\u00E1ximo de sensores";
		}
	}

	public static String minAccuracy() {
		if (de) {
			return "Minimale Genauigkeit";
		} else if (en) {
			return "Minimal accuracy";
		} else {
			return "N\u00FAmero m\u00EDnimo de sensores";
		}
	}

	public static String maxAccuracy() {
		if (de) {
			return "Maximale Genauigkeit";
		} else if (en) {
			return "Maximal accuracy";
		} else {
			return "N\u00FAmero m\u00E1ximo de precisi\u00F3n";
		}
	}

	public static String unit() {
		if (de) {
			return "Einheit";
		} else if (en) {
			return "Unit";
		} else {
			return "Unidad";
		}
	}

	public static String minValue() {
		if (de) {
			return "Minimaler Wert";
		} else if (en) {
			return "Minimal value";
		} else {
			return "Valor m\u00EDnimo ";
		}
	}

	public static String maxValue() {
		if (de) {
			return "Maximaler Wert";
		} else if (en) {
			return "Maximal value";
		} else {
			return "Valor m\u00E1ximo";
		}
	}

	public static String minDate() {
		if (de) {
			return "Minimales Datum";
		} else if (en) {
			return "Minimal date";
		} else {
			return "Fecha m\u00EDnimo";
		}
	}

	public static String maxDate() {
		if (de) {
			return "Maximales Datum";
		} else if (en) {
			return "Maximal date";
		} else {
			return "Fecha m\u00E1ximo";
		}
	}

	public static String minTime() {
		if (de) {
			return "Minimale Zeit";
		} else if (en) {
			return "Minimal time";
		} else {
			return "Tiempo m\u00EDnimo";
		}
	}

	public static String maxTime() {
		if (de) {
			return "Maximale Zeit";
		} else if (en) {
			return "Maximum time";
		} else {
			return "Tiempo m\u00E1ximo";
		}
	}

	public static String type() {
		if (de) {
			return "Typ";
		} else if (en) {
			return "Type";
		} else {
			return "Tipo";
		}
	}

	public static String measurand() {
		if (de) {
			return "Messgrösse";
		} else if (en) {
			return "Measurand";
		} else {
			return "Mensurando";
		}
	}

	public static String all() {
		if (de) {
			return "Alle";
		} else if (en) {
			return "All";
		} else {
			return "Todos";
		}
	}

	public static String languageChange() {
		if (de) {
			return "EN";
		} else if (en) {
			return "DE";
		} else {
			return "ES";
		}
	}

	public static String past24Hours() {
		if (de) {
			return "Letzte 24h";
		} else if (en) {
			return "Past 24h";
		} else {
			return "Pasadas 24h";
		}
	}

	public static String pastWeek() {
		if (de) {
			return "Letzte Woche";
		} else if (en) {
			return "Past week";
		} else {
			return "Pasada semana";
		}
	}

	public static String pastMonth() {
		if (de) {
			return "Letzter Monat";
		} else if (en) {
			return "Past month";
		} else {
			return "Pasado mes";
		}
	}

	public static String pastYear() {
		if (de) {
			return "Letztes Jahr";
		} else if (en) {
			return "Past year";
		} else {
			return "Pasado a\u00F1o";
		}
	}

	public static String customRange() {
		if (de) {
			return "Benutzerdefiniert";
		} else if (en) {
			return "Custom range";
		} else {
			return "Rango personalizado";
		}
	}

	public static String timespan() {
		if (de) {
			return "Zeitspanne";
		} else if (en) {
			return "Timespan";
		} else {
			return "Espacio de tiempo";
		}
	}

	public static String from() {
		if (de) {
			return "Von";
		} else if (en) {
			return "From";
		} else {
			return "Desde";
		}
	}

	public static String to() {
		if (de) {
			return "Bis";
		} else if (en) {
			return "To";
		} else {
			return "A";
		}
	}

	public static String seletedShowOnMap() {
		if (de) {
			return "Auf Karte zeigen";
		} else if (en) {
			return "Show on map";
		} else {
			return "Mostrar en el mapa";
		}
	}

	public static String seletedShowOnSearch() {
		if (de) {
			return "In Suche zeigen";
		} else if (en) {
			return "Show on search";
		} else {
			return "Mostrar en la b\u00FAsqueda";
		}
	}

	public static String selectedShowVisualisations() {
		if (de) {
			return "Werte anzeigen";
		} else if (en) {
			return "Show values";
		} else {
			return "Mostrar valores";
		}
	}

	public static String selectedShowInSearch() {
		if (de) {
			return "In Suche anzeigen";
		} else if (en) {
			return "Show in search";
		} else {
			return "Mostrar en la b\u00FAsqueda";
		}
	}

	public static String selectedAddToList() {
		if (de) {
			return "Zur Liste hinzufügen";
		} else if (en) {
			return "Add to list";
		} else {
			return "A\u00F1adir a la lista";
		}
	}

	public static String allSelectedSensors() {
		if (de) {
			return "Alle ausgewählten Sensoren:";
		} else if (en) {
			return "All selected sensors";
		} else {
			return "Todos los sensores seleccionados";
		}
	}

	public static String selectAllSensors() {
		if (de) {
			return "Alle auswählen";
		} else if (en) {
			return "Select all";
		} else {
			return "Seleccionar todo";
		}
	}

	public static String deselectAllSensors() {
		if (de) {
			return "Alle abwählen";
		} else if (en) {
			return "Deselect all";
		} else {
			return "Deseleccionar todo";
		}
	}

	public static String backwards() {
		if (de) {
			return "Zum Anfang";
		} else if (en) {
			return "Back to start";
		} else {
			return "Volver al principio";
		}
	}

	public static String forwards() {
		if (de) {
			return "Zum Ende";
		} else if (en) {
			return "Forward to end";
		} else {
			return "Saltar al final";
		}
	}

	public static String forwardsStepByStep() {
		if (de) {
			return "Nächste Seite";
		} else if (en) {
			return "Next page";
		} else {
			return "Siguiente p\u00E1gina";
		}
	}

	public static String backwardsStepByStep() {
		if (de) {
			return "Vorherige Seite";
		} else if (en) {
			return "Previous page";
		} else {
			return "P\u00E1gina anterior";
		}
	}

	public static String setPageNumber(int page, int maxObjectsOnPage, int size) {
		if (de) {
			if (size == 0) {
				return "0-0 von 0";
			}
			return Integer.toString((page * maxObjectsOnPage) + 1) + "-"
			+ ((size > ((page + 1) * maxObjectsOnPage)) ? Integer.toString((page + 1) * maxObjectsOnPage)
					: size)
			+ " von " + Integer.toString(size);
		} else if (en) {
			if (size == 0) {
				return "0-0 of 0";
			}
			return Integer.toString((page * maxObjectsOnPage) + 1) + "-"
			+ ((size > ((page + 1) * maxObjectsOnPage)) ? Integer.toString((page + 1) * maxObjectsOnPage)
					: size)
			+ " of " + Integer.toString(size);
		} else {
			if (size == 0) {
				return "0-0 de 0";
			}
			return Integer.toString((page * maxObjectsOnPage) + 1) + "-"
			+ ((size > ((page + 1) * maxObjectsOnPage)) ? Integer.toString((page + 1) * maxObjectsOnPage)
					: size)
			+ " de " + Integer.toString(size);
		}
	}

	public static String pageConfiguration() {
		if (de) {
			return "Anzahl von Objekten auf einer Seite";
		} else if (en) {
			return "Number of objects on one page";
		} else {
			return "N\u00FAmero de objetos en una p\u00E1gina";
		}
	}

	public static String noData() {
		if (de) {
			return "Es konnten keine Sensoren zu den gewählten Suchparametern oder mit Werten gefunden werden";
		} else if (en) {
			return "No sensors found for the chosen parameters or with data";
		} else {
			return "No se encontraron datos para los par\u00E1metros seleccionados";
		}
	}

	public static String sensorId() {
		return "ID ";
	}

	@SuppressWarnings("deprecation")
	public static String getDate(Date timestamp) {
		return timestamp.toLocaleString();
	}

	public static String username() {
		if (de) {
			return "Benutzername";
		} else if (en) {
			return "Username";
		} else {
			return "nombre de usuario";
		}
	}

	public static String password() {
		if (de) {
			return "Passwort";
		} else if (en) {
			return "Password";
		} else {
			return "Contrase\u00F1a";
		}
	}

	public static String passwordVerify() {
		if (de) {
			return "Passwort bestätigen";
		} else if (en) {
			return "Confirm password";
		} else {
			return "Confirmar contrase\u00F1a";
		}
	}

	public static String firstValue() {
		if (de) {
			return "Erste Messung";
		} else if (en) {
			return "First measurement";
		} else {
			return "Primera medida";
		}
	}

	public static String lastValue() {
		if (de) {
			return "Letze Messung";
		} else if (en) {
			return "Last measurement";
		} else {
			return "\u00DAltima medida";
		}
	}

	public static String noValuePreviewData() {
		if (de) {
			return "Keine Messungen vorhanden";
		} else if (en) {
			return "No measurements available";
		} else {
			return "No hay mediciones disponibles";
		}
	}

	public static String rating(double rating) {
		if (de) {
			return "Genauigkeit: " + rating + " von 5 Sternen";
		} else if (en) {
			return "Accuracy: " + rating + " out of 5 stars";
		} else {
			return "Precisi\u00F3n: " + rating + "de 5 estrellas";
		}
	}

	public static String altitudeAboveGround() {
		if (de) {
			return "Höhe über Grund";
		} else if (en) {
			return "Altitude above ground";
		} else {
			return "Altura sobre el suelo";
		}
	}

	public static String origin() {
		if (de) {
			return "Herkunft";
		} else if (en) {
			return "Origin";
		} else {
			return "Origen";
		}
	}

	public static String register() {
		if (de) {
			return "Registrieren";
		} else if (en) {
			return "Register";
		} else {
			return "Registrarse";
		}
	}

	public static String forgotPassword() {
		if (de) {
			return "Passwort vergessen?";
		} else if (en) {
			return "Forgot password?";
		} else {
			return "\u00BFHas olvidado la contrase\u00F1a?";
		}
	}

	public static String login() {
		if (de) {
			return "Einloggen";
		} else if (en) {
			return "Log in";
		} else {
			return "Iniciar sesi\u00F3n";
		}
	}

	public static String logout() {
		if (de) {
			return "Ausloggen";
		} else if (en) {
			return "Log out";
		} else {
			return "Cerrar sesi\u00F3n";
		}
	}

	public static String loginValidateError() {
		if (de) {
			return "Ungültige E-Mail-Adresse oder ungültiges Kennwort";
		} else if (en) {
			return "Not valid E-mail address or not valid passord";
		} else {
			return "Direcci\u00F3n de correo electr\u00F3nico no v\u00E1lida o contrase\u00F1a no v\u00E1lida";
		}
	}

	public static String accuracy() {
		if (de) {
			return "Genauigkeit";
		} else if (en) {
			return "Accuracy";
		} else {
			return "Precisi\u00F3n";
		}
	}

	public static String sensorTyp() {
		if (de) {
			return "Sensortyp";
		} else if (en) {
			return "Sensormodel";
		} else {
			return "Tipo de sensor";
		}
	}

	public static String noListData() {
		if (de) {
			return "Es wurden noch keine Sensoren zur Liste hinzugefügt";
		} else if (en) {
			return "No sensors existing in this list";
		} else {
			return "No hay sensores existentes en esta lista";
		}
	}

	public static String createNewList() {
		if (de) {
			return "Neue Liste erstellen";
		} else if (en) {
			return "Create new list";
		} else {
			return "Crear nueva lista";
		}
	}

	public static String newList() {
		if (de) {
			return "Neue Liste ";
		} else if (en) {
			return "New list ";
		} else {
			return "Lista nueva";
		}
	}

	public static String favorites() {
		if (de) {
			return "Favoriten";
		} else if (en) {
			return "Favorites";
		} else {
			return "Favoritos";
		}
	}

	public static String selectedSensors() {
		if (de) {
			return "Ausgewälte Sensoren";
		} else if (en) {
			return "Selected sensors";
		} else {
			return "Sensores seleccionados";
		}
	}

	public static String mySensors() {
		if (de) {
			return "Meine Sensoren";
		} else if (en) {
			return "My sensors";
		} else {
			return "Mis sensores";
		}
	}

	public static String createSensor() {
		if (de) {
			return "Sensor erstellen";
		} else if (en) {
			return "Create sensor";
		} else {
			return "Crear sensor";
		}
	}

	public static String hideSensorWIithoutValues() {
		if (de) {
			return "Sensoren ohne Werte ausblenden";
		} else if (en) {
			return "Sensors without values wont be visible";
		} else {
			return "Ocultar sensores sin valores.";
		}
	}

	public static String license() {
		if (de) {
			return "Lizenz";
		} else if (en) {
			return "License";
		} else {
			return "Licencia";
		}
	}

	public static String latitude() {
		if (de) {
			return "Breitengrad";
		} else if (en) {
			return "Latitude";
		} else {
			return "Grado de latitud";
		}
	}

	public static String longitude() {
		if (de) {
			return "Längenengrad";
		} else if (en) {
			return "Longitude";
		} else {
			return "grado de longitud";
		}
	}

	public static String directionVertical() {
		if (de) {
			return "Vertikale Ausrichtung";
		} else if (en) {
			return "Vertical direction";
		} else {
			return "Alineaci\u00F3n vertical";
		}
	}

	public static String directionHorizontal() {
		if (de) {
			return "Horizontale Ausrichtung";
		} else if (en) {
			return "Horizontal direction";
		} else {
			return "Alineaci\u00F3n horizontal";
		}
	}

	public static String sensorModel() {
		if (de) {
			return "Sensor-Modell";
		} else if (en) {
			return "Sensor model";
		} else {
			return "Modelo de sensor";
		}
	}

	public static String attributionText() {
		if (de) {
			return "Zuordnungstext";
		} else if (en) {
			return "Attribution text";
		} else {
			return "Texto de atribuci\u00F3n";
		}
	}

	public static String attributionURL() {
		if (de) {
			return "URL";
		} else if (en) {
			return "Attribution URL";
		} else {
			return "URL de atribuci\u00F3n";
		}
	}

	public static String confirm() {
		if (de) {
			return "Bestätigen";
		} else if (en) {
			return "Confirm";
		} else {
			return "Confirmar";
		}
	}

	public static String cancel() {
		if (de) {
			return "Abbrechen";
		} else if (en) {
			return "Cancel";
		} else {
			return "Cancelar";
		}
	}

	public static String notLoggedIn() {
		if (de) {
			return "Nicht eingeloggt";
		} else if (en) {
			return "Not logged in";
		} else {
			return "No has iniciado sesi\u00F3n";
		}
	}

	public static String sensorCreated() {
		if (de) {
			return "Sensor erstellt";
		} else if (en) {
			return "Sensor created";
		} else {
			return "Sensor creado";
		}
	}

	public static String sensorDeleted() {
		if (de) {
			return "Sensor gelöscht";
		} else if (en) {
			return "Sensor deleted";
		} else {
			return "Sensor eliminado";
		}
	}

	public static String invalidParameters() {
		if (de) {
			return "Ungültige Parameter";
		} else if (en) {
			return "Invalid parameters";
		} else {
			return "Par\u00E1metros inv\u00E1lidos";
		}
	}

	public static String with() {
		if (de) {
			return "mit";
		} else if (en) {
			return "with";
		} else {
			return "con";
		}
	}

	public static String connectionError() {
		if (de) {
			return "Es ist ein Fehler aufgetreten. Wiederholen Sie Ihre Anfrage oder versuchen Sie es zu einem späterem Zeitpunkt erneut.";
		} else if (en) {
			return "An error has occurred. Repeat your request or try again later.";
		} else {
			return "Ha ocurrido un error. Repita su solicitud o intente de nuevo m\u00E1s tarde";
		}
	}

	public static String selectListItemError() {
		if (de) {
			return "Es ist ein Fehler beim auswählen der Liste aufgetreten.";
		} else if (en) {
			return "There was an error while selecting the list.";
		} else {
			return "Hubo un error al seleccionar la lista.";
		}
	}

	public static String maxSensorSelectedLimitExceeded(int maxSensors) {
		if (de) {
			return "Sie können nicht mehr als " + maxSensors + " Sensoren auswählen.";
		} else if (en) {
			return "You can not select more than" + maxSensors + " sensors.";
		} else {
			return "No puedes seleccionar m\u00E1s de" + maxSensors + " sensores.";
		}
	}

	public static String maxFavoriteSensorsReached(int maxSensors) {
		if (de) {
			return "Die Favoriten dürfen maximal " + maxSensors + " enthalten.";
		} else if (en) {
			return "Your favorites may contain a maximum of"+ maxSensors + "sensors";
		} else {
			return "Su lista de favoritos puede contener un m\u00E1ximo de " + maxSensors + " sensores";
		}
	}

	public static String deleteSensors() {
		if (de) {
			return "Sensoren löschen";
		} else if (en) {
			return "Delete sensor";
		} else {
			return "Eliminar sensor";
		}
	}

	public static String searchForPlace() {
		if (de) {
			return "Nach Ort suchen";
		} else if (en) {
			return "Search for place";
		} else {
			return "Buscar ubicaci\u00F3n";
		}
	}

	public static String addedSensorsToList(String sensors, String listName, boolean moreThanOne) {
		if (de) {
			return (moreThanOne ? "Die Sensoren " + sensors + " wurden" : "Der Sensor " + sensors + " wurde")
					+ " erfolgreich zur Liste \"" + listName + "\" hinzugefügt";
		} else if (en) {
			return (moreThanOne ? "The sensors " : "The sensor ") + sensors + " got added successfully to the list \""
					+ listName + "\"";
		} else {
			return (moreThanOne ? "Los sensores " : "El sensor ") + sensors
					+ " se han a\u00F1adido con \u00E9xito a la lista \"" + listName + "\"";
		}
	}

	public static String userId() {
		if (de) {
			return "Benutzer-ID";
		} else if (en) {
			return "User ID";
		} else {
			return "ID de usuario";
		}
	}

	public static String values() {
		if (de) {
			return "Werte";
		} else if (en) {
			return "Values";
		} else {
			return "Valores";
		}
	}

	public static String containsAlready(String sensors, String listName, boolean moreThanOne) {
		if (de) {
			return (moreThanOne ? "Die Sensoren " + sensors + " wurden" : "Der Sensor " + sensors + " wurde")
					+ " bereits zur Liste \"" + listName + "\" hinzugefügt";
		} else if (en) {
			return (moreThanOne ? "The sensors " + sensors + " have" : "The sensor " + sensors + " has")
					+ " already been added to the list \"" + listName + "\"";
		} else {
			return (moreThanOne ? "Los sensores" + sensors + " ya se han" : "EL sensor " + sensors + "ya se ha")
					+ " a\u00F1adido a la lista \"" + listName + "\"";
		}
	}

	public static String removeSensorsFromList(String sensors, String listName, boolean moreThanOne) {
		if (de) {
			return (moreThanOne ? "Die Sensoren " + sensors + " wurden" : "Der Sensor " + sensors + " wurde")
					+ " aus der Liste \"" + listName + "\" gelöscht";
		} else if (en) {
			return (moreThanOne ? "The sensors " + sensors + " were" : "The sensor " + sensors + " was")
					+ " deleted from the list \"" + listName + "\"";
		} else {
			return (moreThanOne ? "Los sensores " + sensors + " se han" : "El sensor " + sensors + "se ha")
					+ " eliminado de la lista \"" + listName + "\"";
		}
	}

	public static String listContains(int sensorsSize, int selectedSensorsSize) {
		if (de) {
			return "(Enthält " + sensorsSize + (sensorsSize != 1 ? " Sensoren" : " Sensor") + ", davon ausgewählt: "
					+ selectedSensorsSize + ")";
		} else if (en) {
			return "(Contains " + sensorsSize + (sensorsSize != 1 ? " sensors" : " sensor") + ", selected of these: "
					+ selectedSensorsSize + ")";
		} else {
			return "(Contiene " + sensorsSize + (sensorsSize != 1 ? " sensores" : " sensor")
					+ ", seleccionado de estos: " + selectedSensorsSize + ")";
		}
	}

	public static String errorMessageAccuracy() {
		if (de) {
			return "Bitte geben Sie eine Zahl zwischen 0 und 10 ein";
		} else if (en) {
			return "Please enter a number between 0 and 10";
		} else {
			return "Por favor ingrese un n\u00FAmero entre 0 y 10 ";
		}
	}

	public static String errorMessageMaxSens() {
		if (de) {
			return "Bitte geben Sie eine Zahl zwischen 1 und 19999 ein";
		} else if (en) {
			return "Please enter a number between 1 and 19999";
		} else {
			return "Por favor ingrese un n\u00FAmero entre 1 y 19999";
		}
	}

	public static String noSensorsSelected() {
		if (de) {
			return "Keine Sensoren ausgewählt";
		} else if (en) {
			return "No sensors selected";
		} else {
			return "No has seleccionado sensores";
		}
	}

	public static String information() {
		if (de) {
			return "Information zeigen";
		} else if (en) {
			return "Show information";
		} else {
			return "Mostrar informacion";
		}
	}

	public static String searchTooltip() {
		if (de) {
			return "Sensoren anhand der angegeben Parameter suchen";
		} else if (en) {
			return "Search sensors on the basis of the given parameters";
		} else {
			return "Buscar sensores utilizando los par\u00E1metros especificados";
		}
	}

	public static String selectAllTooltip() {
		if (de) {
			return "Alle Sensoren in der Liste auswählen";
		} else if (en) {
			return "Select all sensors displayed in the list";
		} else {
			return "Seleccionar todos los sensores en la lista";
		}
	}

	public static String deselectAllTooltip() {
		if (de) {
			return "Alle ausgewählten Sensoren abwählen";
		} else if (en) {
			return "Deselect all selected sensors";
		} else {
			return "Deseleccionar todos los sensores en la lista";
		}
	}

	public static String showOnMapTooltip() {
		if (de) {
			return "Ausgewählte Sensoren auf der Karte anzeigen";
		} else if (en) {
			return "Show selected sensors on the map";
		} else {
			return "Mostrar sensores seleccionados en el mapa";
		}
	}

	public static String showValuesTooltip() {
		if (de) {
			return "Werte aller ausgewählten Sensoren im Diagramm anzeigen";
		} else if (en) {
			return "Show the values of the selected sensors in a chart";
		} else {
			return "Mostrar los valores de los sensores seleccionados en un gr\u00E1fico";
		}
	}

	public static String addToListTooltip() {
		if (de) {
			return "Alle ausgewählten Sensoren zu einer Liste hinzufügen";
		} else if (en) {
			return "Add all selected sensors to a list";
		} else {
			return "A\u00F1adir todos los sensores seleccionados a una lista";
		}
	}

	public static String favTooltip() {
		if (de) {
			return "Zu Favoriten hinzufügen";
		} else if (en) {
			return "Add to favorites";
		} else {
			return "A\u00F1adir a los favoritos";
		}
	}

	public static String infoTooltip() {
		if (de) {
			return "Informationen anzeigen";
		} else if (en) {
			return "Show information";
		} else {
			return "Mostrar informacion";
		}
	}

	public static String deleteTooltip() {
		if (de) {
			return "Aus der Liste löschen";
		} else if (en) {
			return "Delete from list";
		} else {
			return "Eliminar de la lista";
		}
	}

	public static String showInSearchTooltip() {
		if (de) {
			return "Ausgewählte Sensoren in der Suche anzeigen";
		} else if (en) {
			return "Show selected sensors in the search view";
		} else {
			return "Mostrar sensores seleccionados en la b\u00FAsqueda";
		}
	}

	public static String welcome() {
		if (de) {
			return "Willkommen: ";
		} else if (en) {
			return "Welcome: ";
		} else {
			return "Bienvenido";
		}
	}

	public static String guest() {
		if (de) {
			return "Gast";
		} else if (en) {
			return "Guest";
		} else {
			return "Hu\u00E9sped";
		}
	}

	public static String welcomeInfoText() {
		if (de) {
			return "Herzlichen Willkommen beim OpenSense-Dashboard, hier können Sie die Daten von Weltweit verteilten Sensoren einsehen. "
					+ "Da es sich um mehrere 10 tausend Sensoren handelt, bieten wir mit OpenSense-Dashboard eine Plattform in der Sie nach "
					+ "bestimmten Sensormessgrößen, Ortsabhängig und unkompliziert suchen können.";
		} else if (en) {
			return "Welcome to the OpenSense Dashboard, where you can view the data from Global Sensors."
					+ "With tens of thousands of sensors, OpenSense Dashboard provides a platform where you can search for specific sensor readings,"
					+ " location-dependent, and uncomplicated."
					+ "If some sensors are of particular importance to you and you do not want to search for them again and again,"
					+ " we also offer you the possibility to create your own lists in which you can then insert the sensors."
					+ "So that you do not only see the sensor data in text form, you can also view the sensors on a map provided by Google Maps,"
					+ " or simply view the corresponding data in a diagram.";
		} else {
			return "Bienvenido a OpenSense Dashboard, donde puede ver los datos de sensores distribuidos globalmente."
					+ "Con decenas de miles de sensores, OpenSense Dashboard proporciona una plataforma donde puede buscar sensores"
					+ " con medidas espec\u00EDficas,"
					+ " dependientes de la ubicaci\u00F3n y sin complicaciones."
					+ " Si algunos sensores son de particular importancia para usted y no desea buscarlos una y otra vez, tambi\u00E9n le ofrecemos la posibilidad"
					+ " de crear sus propias listas en las que puede insertar los sensores ."
					+ " Para no solo ver los datos de los sensores en forma de texto, tambi\u00E9n puede ver los sensores en un mapa proporcionado por Google Maps,"
					+ " o simplemente ver los datos correspondientes en un diagrama.";
		}
	}
	
	public static String welcomeInfoText1() {
		if (de) {
			return  "Falls ihnen dabei einige Sensoren besonder wichtig sind und Sie nicht jedes Mal erneut nach ihnen suchen wollen, "
					+ "bieten wir ihnen zusätzlich die Möglichkeit eigene Listen zu erstellen in die Sie dann die Sensoren einfügen können.";
		} else if (en) {
			return "Welcome to the OpenSense Dashboard, where you can view the data from Global Sensors."
					+ "With tens of thousands of sensors, OpenSense Dashboard provides a platform where you can search for specific sensor readings,"
					+ " location-dependent, and uncomplicated."
					+ "If some sensors are of particular importance to you and you do not want to search for them again and again,"
					+ " we also offer you the possibility to create your own lists in which you can then insert the sensors."
					+ "So that you do not only see the sensor data in text form, you can also view the sensors on a map provided by Google Maps,"
					+ " or simply view the corresponding data in a diagram.";
		} else {
			return "Bienvenido a OpenSense Dashboard, donde puede ver los datos de sensores distribuidos globalmente."
					+ "Con decenas de miles de sensores, OpenSense Dashboard proporciona una plataforma donde puede buscar sensores"
					+ " con medidas espec\u00EDficas,"
					+ " dependientes de la ubicaci\u00F3n y sin complicaciones."
					+ " Si algunos sensores son de particular importancia para usted y no desea buscarlos una y otra vez, tambi\u00E9n le ofrecemos la posibilidad"
					+ " de crear sus propias listas en las que puede insertar los sensores ."
					+ " Para no solo ver los datos de los sensores en forma de texto, tambi\u00E9n puede ver los sensores en un mapa proporcionado por Google Maps,"
					+ " o simplemente ver los datos correspondientes en un diagrama.";
		}
	}
	
	public static String welcomeInfoText2() {
		if (de) {
			return  "Damit Sie die Sensordaten nicht nur in Textform sehen, können Sie die Sensoren auf OpenSense-Dashboard auch auf "
					+ "einer von Google Maps bereitgestellten Karte einsehen, oder aber die dazugehörigen Daten einfach in einem Diagramm einsehen.";
		} else if (en) {
			return "Welcome to the OpenSense Dashboard, where you can view the data from Global Sensors."
					+ "With tens of thousands of sensors, OpenSense Dashboard provides a platform where you can search for specific sensor readings,"
					+ " location-dependent, and uncomplicated."
					+ "If some sensors are of particular importance to you and you do not want to search for them again and again,"
					+ " we also offer you the possibility to create your own lists in which you can then insert the sensors."
					+ "So that you do not only see the sensor data in text form, you can also view the sensors on a map provided by Google Maps,"
					+ " or simply view the corresponding data in a diagram.";
		} else {
			return "Bienvenido a OpenSense Dashboard, donde puede ver los datos de sensores distribuidos globalmente."
					+ "Con decenas de miles de sensores, OpenSense Dashboard proporciona una plataforma donde puede buscar sensores"
					+ " con medidas espec\u00EDficas,"
					+ " dependientes de la ubicaci\u00F3n y sin complicaciones."
					+ " Si algunos sensores son de particular importancia para usted y no desea buscarlos una y otra vez, tambi\u00E9n le ofrecemos la posibilidad"
					+ " de crear sus propias listas en las que puede insertar los sensores ."
					+ " Para no solo ver los datos de los sensores en forma de texto, tambi\u00E9n puede ver los sensores en un mapa proporcionado por Google Maps,"
					+ " o simplemente ver los datos correspondientes en un diagrama.";
		}
	}
	
	
	public static String searchInfoText() {
		if (de) {
			return "Auf dieser Seite können Sie Sensoren suchen und ihre Informationen einsehen."
					+ "Die Sensoren können parametrisiert sowie Ortsabhängig gesucht werden.";
		} else if (en) {
			return "On this page you can search the sensors.\r\n"
					+ "The sensors can be parameterized and searched for location-dependent.";
		} else {
			return "En esta p\u00E1gina puede buscar los sensores.\r\n"
					+ "Los sensores pueden parametrizarse y buscarse seg\u00FAn la ubicaci\u00F3n.";
		}
	}

	public static String mapInfoText() {
		if (de) {
			return "Auf der von Google bereitgestellten Karte können Sie ihre Sensoren anzeigen lassen."
					+ "Die Sensoren werden abhängig von ihrem Typ mit einem anderen Symbol auf der Karte angezeigt."
					+ "Sollte sich mehrere Sensoren auf einer Stelle befinden so wird dies durch ein gro\u00DFes plus zeichen deutlich.";
		} else if (en) {
			return "You can have your sensors displayed on the map provided by Google."
					+ " The sensors are displayed with a different symbol on the map depending on their type"
					+ " If several sensors are in one place, this is indicated by a large plus sign.";
		} else {
			return "Puede tener sus sensores mostrados en el mapa provisto por Google. "
					+ "Los sensores se muestran con un s\u00EDmbolo diferente en el mapa dependiendo de su tipo "
					+ "Si hay varios sensores en un solo lugar, esto se indica por un signo de m\u00E1s.";
		}
	}

	public static String visuInfoText() {
		if (de) {
			return "Die hier bereitgestellte Funktionalität ermöglicht es ihnen,"
					+ " die Werte der Sensoren je nach belieben (Tag, Monat, Jahr, oder spezifischer), in einem Diagramm darzustellen."
					+ "Dabei behalten Sie dank der farblichen Markierung der Angezeigten Sensoren immer den Überblick."
					+ "Bitte beachten Sie dabei, dass Sie hier maximal 10 Sensoren zur gleichen Zeit in den Listen auswählen kannst.";
		} else if (en) {
			return "The functionality provided here allows you to display the values of the sensors as you like (day, month, year, or more specific) in a diagram."
					+ " Thanks to the colored marking of the displayed sensors, you always have the overview."
					+ " Please note that you can select a maximum of 10 sensors here at the same time in the lists.";
		} else {
			return "La funcionalidad proporcionada aqu\u00ED le permite ver los valores de los sensores como desee"
					+ " (d\u00EDa, mes, a\u00F1o o m\u00E1s espec\u00EDficos) en un diagrama."
					+ " Gracias a la marca de color de los sensores mostrados, siempre tiene la visi\u00F3n general."
					+ " Por favor tenga en cuenta que puede seleccionar un m\u00E1ximo de 10 sensores al mismo tiempo aqui en las listas.";
		}
	}

	public static String listInfoText() {
		if (de) {
			return "Damit Sie nicht immer wieder dieselben Sensoren auf der Suchseite suchen m\u00FCssen, k\u00F6nnen Sie eigene Listen erstellen. "
					+ "Erstellen Sie eine Liste und fügen Sie über die Suchseite passende Sensoren hinzu."
					+ "Außerdem können Sie, wenn Sie einen entsprechenden Account besitzen, auch einfach ihre eigenen Sensoren zu OpenSense hinzufügen."
					+ "Um das importieren ihrer Sensordaten einfacher zu gestalen, können Sie ihre Sensordaten als \".csv\" Datei hochladen.";
		} else if (en) {
			return "To avoid having to search for the same sensors on the search page again and again, you can create your own lists."
					+ " Create a list and add suitable sensors via the search page. "
					+ " Plus, if you have an account, you can easily add your own sensors to OpenSense."
					+ " To make the import of your sensor data easier, you can upload your sensor data as a \".csv \" file.";
		} else {
			return "Para evitar tener que buscar los mismos sensores en la p\u00E1gina de b\u00FAsqueda una y otra vez, puede crear sus propias listas."
					+ " Cree una lista y agregue sensores adecuados a trav\u00E9s de la p\u00E1gina de b\u00FAsqueda."
					+ " Adem\u00E1s, si tiene una cuenta, puede agregar f\u00E1cilmente sus propios sensores a OpenSense."
					+ " Para facilitar la importaci\u00F3n de los datos de su sensor, puede cargar sus datos de sensor como un archivo \".csv\" .";
		}
	}

	public static String german() {
		if (de) {
			return "Deutsch";
		} else if (en) {
			return "German";
		} else {
			return "Alem\u00E1n";
		}
	}

	public static String english() {
		if (de) {
			return "Englisch";
		} else if (en) {
			return "English";
		} else {
			return "Ingl\u00E9s";
		}
	}

	public static String spanish() {
		if (de) {
			return "Spanisch";
		} else if (en) {
			return "Spanish";
		} else {
			return "Espa\u00F1ol";
		}
	}

	public static String delete() {
		if (de) {
			return "Löschen";
		} else if (en) {
			return "Delete";
		} else {
			return "Eliminar";
		}
	}

	public static String confirmDeleteQuestion() {
		if (de) {
			return "Möchten Sie die Liste wirklich löschen?";
		} else if (en) {
			return "Do you really want to delete the list?";
		} else {
			return "\u00BFDe verdad quieres borrar esta lista?";
		}
	}

	public static String emailAddress() {
		if (de) {
			return "Email-Adresse";
		} else if (en) {
			return "Email address";
		} else {
			return "Direcci\u00F3n de correo electr\u00F3nico";
		}
	}

	public static String passwordsDontMatch() {
		if (de) {
			return "Passwörter stimmen nicht überein";
		} else if (en) {
			return "Passwords do not match";
		} else {
			return "Las contrase\u00F1as no coinciden";
		}
	}

	public static String invalidEmail() {
		if (de) {
			return "Ungültige Email-Adresse";
		} else if (en) {
			return "Invalid email address";
		} else {
			return "direcci\u00F3n de correo electr\u00F3nico inv\u00E1lida";
		}
	}

	public static String invalidUsername() {
		if (de) {
			return "Ungültiger Benutzername";
		} else if (en) {
			return "Invalid username";
		} else {
			return "Nombre de usuario inv\u00E1lido";
		}
	}

	public static String send() {
		if (de) {
			return "Absenden";
		} else if (en) {
			return "Send";
		} else {
			return "Enviar";
		}
	}

	public static String passwordResetSent() {
		if (de) {
			return "Passwort-Zurücksetzung wurde gesendet";
		} else if (en) {
			return "Password reset was sent";
		} else {
			return "Se ha enviado un restablecimiento de contrase\u00F1a.";
		}
	}

	public static String loggedOut() {
		if (de) {
			return "Erfolgreich abgemeldet";
		} else if (en) {
			return "Successfully logged out";
		} else {
			return "Desconectado exitosamente";
		}
	}

	public static String loggedIn() {
		if (de) {
			return "Erfolgreich angemeldet";
		} else if (en) {
			return "Succesfully logged in";
		} else {
			return "Ha iniciado sesi\u00F3n correctamente";
		}
	}

	public static String changePassword() {
		if (de) {
			return "Passwort ändern";
		} else if (en) {
			return "Change password";
		} else {
			return "Cambia la contrase\u00F1a";
		}
	}

	public static String saveNewPassword() {
		if (de) {
			return "Passwort speichern";
		} else if (en) {
			return "Save new passoword";
		} else {
			return "Guardar contrase\u00F1a";
		}
	}

	public static String verifyNewPassword() {
		if (de) {
			return "Neues Passwort bestätigen";
		} else if (en) {
			return "Verify new password";
		} else {
			return "Verificar nueva contrase\u00F1a";
		}
	}

	public static String oldPassword() {
		if (de) {
			return "Altes Passwort";
		} else if (en) {
			return "Old password";
		} else {
			return "Contrase\u00F1a anterior";
		}
	}

	public static String newPassword() {
		if (de) {
			return "Neues Passwort";
		} else if (en) {
			return "New password";
		} else {
			return "Nueva contrase\u00F1a";
		}
	}

	public static String goBack() {
		if (de) {
			return "Zurück";
		} else if (en) {
			return "Back";
		} else {
			return "Atr\u00E1s";
		}
	}

	public static String successfullyChangedPassword() {
		if (de) {
			return "Das Passwort wurde erfolgreich geändert";
		} else if (en) {
			return "The password was successfully changed";
		} else {
			return "La contrase\u00F1a se cambi\u00F3 con \u00E9xito";
		}
	}

	public static String successfullyCreatedAccout() {
		if (de) {
			return "Sie haben sich erfolgreich registriert";
		} else if (en) {
			return "You have successfully registered";
		} else {
			return "Te has registrado exitosamente";
		}
	}

	public static String hideInformation() {
		if (de) {
			return "Informationen ausblenden";
		} else if (en) {
			return "Hide information";
		} else {
			return "Ocultar información";
		}
	}

	public static String tourError() {
		if (de) {
			return "Beim ausführen der Tour ist ein Fehler aufgetreten. Versuchen Sie unter Hilfe die Tour erneut zu starten. "
					+ "Sollte der Fehler immer noch auftreten, kontaktieren Sie uns bitte!";
		} else if (en) {
			return "While running the tour an error occues. Please try to start the tour again at the help page. If the error"
					+ " still occurs, please contact us!";
		} else {
			return "Durante la ejecuci\u00F3n del recorrido se produjo un error. Por favor, intente comenzar el recorrido nuevamente en la p\u00E1gina de ayuda. Si el error persiste, cont\u00E1ctenos!";
		}
	}

	public static String exitTourFirst() {
		if (de) {
			return "Zum beenden des Rundgangs";
		} else if(en) {
			return "Press";
		} else {
			return "Presiona";
		}
	}

	public static String exitTourSecond() {
		if (de) {
			return "drücken";
		} else if(en) {
			return "to exit the tour";
		} else {
			return "para terminar el recorrido";
		}
	}

	public static String startTour() {
		if (de) {
			return "Tour starten";
		} else if(en) {
			return "Start tour";
		}else {
			return "Empezar recorrido";
		}
	}

	public static String continueStep() {
		if (de) {
			return "Weiter";
		} else if(en) {
			return "Continue";
		}else {
			return "Continuar";
		}
	}

	public static String noTourData() {
		if (de) {
			return "Keine Touren verfügbar";
		} else if(en) {
			return "No tours available";
		} else {
			return "No hay tour disponible";
		}
	}

	public static String closeTour() {
		if (de) {
			return "Tour beenden";
		} else if(en) {
			return "Exit tour";
		}else {
			return "Terminar el recorrido";
		}
	}

	public static String dontShowTours() {
		if (de) {
			return "Keine Touren mehr anzeigen";
		} else if(en) {
			return "Do not show any tours";
		}else {
			return "No mostrar ning\u00FAn recorrido";
		}
	}

	public static String tourHeader(int tourStep, int tourSteps) {
		if (de) {
			return "Rundgang: Schritt " + tourStep + " von " + tourSteps;
		} else if(en) {
			return "Tour: Step " + tourStep + " of " + tourSteps;
		}else {
			return "Tour: Paso" + tourStep + " de "+ tourSteps;
		}
	}

	public static String showTourOnPage() {
		if (de) {
			return "Touren automatisch anzeigen, wenn der Startpunkt aufgerufen wird";
		} else if(en) {
			return "Show tours automaticaly if you reaching the startpoint";
		}else {
			return "Mostrar recorridos autom\u00E1ticamente al llegar al punto de inicio.";
		}
	}

	public static String clearFinishedTours() {
		if (de) {
			return "Alle Rundgänge erneut starten";
		} else if(en) {
			return "Start all tours again";
		} else {
			return "Reiniciar todos los recorridos";
		}
	}

	public static String infoTours() {
		if (de) {
			return "Hinweis: Rundgänge starten automatisch, wenn Sie das erste mal auf die Seite kommen. "
					+ "Wenn Sie alle Rundgänge erneut starten, bekommen Sie bereits beendet erneut angezeigt.";
		} else if(en) {
			return "Note: Tours start automatically when you first come to the page."
					+ "If you start all tours again, you will get displayed already finished again.";
		}else {
			return "Nota: los tours comienzan autom\u00E1ticamente cuando usted llega a la p\u00E1gina."
					+ "Si vuelve a iniciar todos los recorridos, se mostrar\u00E1 ya terminado otra vez.";
		}
	}

	public static String measurandsTour() {
		if (de) {
			return "Wenn Sie nur nach Sensoren mit einer bestimmten Messgröße suchen wollen, können Sie diese in der Liste auswählen.";
		} else if(en) {
			return "You can choose a specific measurand from the list if you only want to search for sensors that measure this measurand.";
		} else {
			return "Puede elegir un mensurando espec\u00EDfico de la lista si solo desea buscar sensores que midan este mensurando.";
		}
	}

	public static String placeInputTour() {
		if (de) {
			return "Hier k\u00F6nnen Sie nach einem bestimmten Ort suchen. Die Suche gibt nur Sensoren an dieser Stelle zur\u00FCck.";
		} else if(en) {
			return "Here you can search for a certain place. The search will only return sensors at this place.";
		}else {
			return "Aqu\u00ED puede buscar un lugar determinado. La b\u00FAsqueda solo devolver\u00E1 sensores en este lugar.";
		}
	}

	public static String maxSensorsTour() {
		if (de) {
			return "Geben Sie immer eine maximale Anzahl von Sensoren ein, sonst dauern Anfragen sehr lange. Wir empfehlen außerdem niemals mehr als 1000 Sensoren anzufragen, da sie bis zu 5 Minuten dauern kann."
					+ "Tragen Sie beispielweise 50 ein und drücken \"Weiter\".";
		} else if(en) {
			return "Enter a number (e.g. 50) to limit the amount of sensors that the search will return. Lower numbers will result in faster searches. Once you have entered a number, press \"Continue\".";
		} else {
			return "Siempre ingrese un n\u00FAmero m\u00E1ximo de sensores, de lo contrario las solicitudes tomar\u00E1n mucho tiempo."
					+ " Tambi\u00E9n recomendamos nunca pedir m\u00E1s de 1000 sensores, ya que puede demorar hasta 5 minutos."
					+ "Por ejemplo, ingrese 50 y presione \"Continuar \".";
		}
	}

	public static String accuracyTour() {
		if (de) {
			return "Sie können eine Genauigkeit der Sensoren angeben auf einer Skala von 0-10. Die Genauigkeit wird Ihnen als Sternebewertung in den Sensoren angezeigt.";
		} else if(en) {
			return "You can enter a minimum or maximum accuracy (0-10) for the sensors here. The accuracy will be displayed as a \"x out of 5 stars\" rating.";
		} else {
			return "Puede especificar una precisi\u00F3n de los sensores en una escala de 0-10. La precisi\u00F3n se muestra como una calificaci\u00F3n de estrellas en los sensores.";
		}
	}

	public static String searchButtonTour() {
		if (de) {
			return "Suchen Sie Sensoren anhand der gewählten Parameter indem Sie den Knopf betätigen.";
		} else if(en) {
			return "Start the search by clicking this button.";
		} else {
			return "Encuentra sensores basados en los par\u00E1metros seleccionados presionando el bot\u00F3n.";
		}
	}

	public static String sensorsWithValuesTour() {
		if (de) {
			return "Wenn Sie Sensoren, welche keine Werte gesammelt haben ausschließen möchten, lassen Sie das Kontrollkästchen ausgewählt.";
		} else if (en) {
			return "If you don't want to see any sensors without values in the search result, check this box.";
		} else {
			return "Si no desea ver ning\u00FAn sensor sin valores en el resultado de b\u00FAsqueda, marque esta casilla.";
		}
	}

	public static String createListButtonTour() {
		if (de) {
			return "Sie können eine neue Liste erstellen und sie beliebig benennen. Wenn Sie ihrer Liste dann Sensoren hinzufügen möchten, dann gehen Sie bitte auf die Suchseite und fügen dort die entsprechenden Sensoren ein.";
		} else if (en) {
			return "You can create a new list and name it as you like. If you would like to add sensors to your list, then please go to the search page and insert the appropriate sensors there.";
		} else {
			return "Puedes crear una nueva lista y nombrarla como quieras. Si desea agregar sensores a su lista, por favor vaya a la p\u00E1gina de b\u00FAsqueda e inserte los sensores apropiados all\u00ED.";
		}
	}

	public static String createSensorButtonTour() {
		if (de) {
			return "Sie k\u00F6nnen einen Sensor zu erstellen welcher zu OpenseSense hinzugef\u00FCgt wird. Bitte achten Sie darauf ihre Daten vor dem Abschicken noch einmal zu Überprüfen. Ihre eigenen Sensoren können sie anschließend jeder Zeit hier auf der Listseite einsehen und löschen.";
		} else if (en) {
			return "You can create a sensor which will be added to OpenseSense. Please make sure to check your data again before submitting. You can then view and delete your own sensors here on the list page at any time.";
		} else {
			return "Usted puede crear un sensor que se agregar\u00E1 a OpenseSense.Por favor, aseg\u00FArese de verificar sus datos nuevamente antes de enviar. Usted puede ver y eliminar sus propios sensores aqu\u00ED en la p\u00E1gina de listas en cualquier momento.";
		}
	}

	public static String addValuesButtonTour() {
		if (de) {
			return "Hier können Sie Werte (in einem bestimmten Format) zu einem von IHNEN erstellten Sensoren hinzufügen.";
		} else if(en) {
			return "Here you can add values (in a certain format) for sensors that YOU created.";
		} else {
			return "Aqu\u00ED puede agregar valores (en un formato determinado) para los sensores que USTED cre\u00F3.";
		}
	}

	public static String listContainerTour() {
		if (de) {
			return "Ihre selbst erstellten Listen können sie hier umbenennen und ihre enthaltene Sensoren einsehen. Sie können Sensoren auswählen und diese löschen oder aber auf einen der Knöpfe drücken um sie auf der entsprechend anderen Seite anzeigen zu lassen.";
		} else if (en) {
			return "You can rename your own lists here and see their included sensors. You can select sensors and delete them, or press one of the buttons to display them on another page.";
		} else {
			return "Puede cambiar el nombre de sus propias listas aqu\u00ED y ver sus sensores incluidos. Usted puede seleccionar sensores y eliminarlos, o presionar uno de los botones para mostrarlos en otra p\u00E1gina.";
		}
	}

	public static String timespanPanelTour() {
		if(de) {
			return "Hier können Sie die Zeitspanne für die angezeigten Werte auswählen.\n Vorausgewählt ist der Zeitraum \"Letzte Woche\".";
		}else if (en) {
			return "Here you can choose the timespan for the shown values.\n The preselected timespan is \"last week\".";
		} else {
			return "Aqu\u00ED puede seleccionar el intervalo de tiempo para los valores mostrados.  \n Preseleccionado es el per\u00EDodo  \"\u00DAltima semana \".";
		}
	}

	public static String listContainerVisTour() {
		if(de) {
			return "Hier werden alle ihre Listen angezeigt. Im Diagramm werden immer die Daten der ausgewählten Sensoren in der jeweils ausgeklappten Liste angezeigt.\nWählen Sie nun einen oder mehrere Sensoren in der Favoriten-Liste aus, um seine/ihre Daten zu sehen.";
		}else if(en) {
			return "All your lists will be displayed here. The data in the diagram always corresponds to the currently selected sensors in the expanded list.\n Select one or more sensors in the Favorites list now in order to see their values.";
		} else {
			return "Aqu\u00ED se muestran todas sus listas. El diagrama siempre muestra los datos de los sensores seleccionados en la lista desplegada. Ahora seleccione uno o m\u00E1s sensores en la lista de Favoritos para ver sus datos.";
		}
	}

	public static String startVisPickerTour() {
		if(de) {
			return "Hier können Sie gegebenenfalls ein Datum auswählen, wenn Sie Daten für einen bestimmten Zeitraum anzeigen lassen möchten.";
		}else if(en) {
			return "If you want to see data from a specific timespan, you can choose it here.";
		}else {
			return "Si desea leer datos de un per\u00EDodo de tiempo espec\u00EDfico, puede elegirlos aqu\u00ED.";
		}
	}

	public static String chartVisContainerTour() {
		if(de) {
			return "Hier werden die Daten der Sensoren in einem Liniendiagramm angezeigt. Wenn Sie die Maus über einen Datenpunkt bewegen, werden Ihnen die genauen Werte in einem Tooltip angezeigt. Falls keine Diagramm angezeigt wird, existieren für die ausgewählten Sensoren und den ausgewählten Zeitraum keine Daten.";
		}else if(en) {
			return "The sensor data is displayed in a line chart here. If you hover over a data point, the exact value will be displayed in a tooltip. If no chart is displayed, there is no data available for the currently selected sensors and the selected timespan.";
		} else {
			return "Aqu\u00ED los datos de los sensores se muestran en un diagrama de l\u00EDneas. Cuando mueve el mouse sobre un punto de datos, los valores exactos se muestran en una informaci\u00F3n sobre herramientas. Si no se muestra ning\u00FAn gr\u00E1fico, no existen datos para los sensores seleccionados y el per\u00EDodo seleccionado.";
		}
	}

	public static String successful() {
		if(de) {
			return "erfolgreich";
		} else if(en) {
			return "successful";
		} else {
			return "exitoso";
		}
	}

	public static String uplaodFile() {
		if(de) {
			return "Datei hochladen";
		} else if(en) {
			return "Upload file";
		} else {
			return "Subir archivo";
		}
	}

	public static String uploadInfo() {
		if(de) {
			return "Laden Sie eine Datei im CSV-Format hoch, in dem Werte mit Zeitstempel für den Sensor stehen. Jede Zeile sollte wie folgt aussehen: \"[Zeitstempel(MM.DD.YYYY,HH:mm:ss)];[Wert]\". Beispiel: 01.01.2000,12:01:00;24.4 ";
		} else if(en) {
			return "Upload a file in CSV format with timestamp values for the sensor. Each line should look like this:"
					+ " \"[Timestamp(MM.DD.YYYY,HH:mm:ss)];[Value]\"."
					+ " Example: 01.01.2000,12:01:00;24.4 ";
		}else {
			return "Cargue un archivo en formato CSV con valores de marca de tiempo para el sensor. Las columnas deben verse as\u00ED  "
					+ "\"\"[FECHA,HORA];[VALOR] -> MM.DD.YYYY,HH:mm:ss;Z.z. por ejemplo 01.01.2000,12:01:00;24.4 \"\".\"";
		}
	}

	public static String addValues() {
		if(de) {
			return "Werte zu einem Sensor hinzufügen";
		} else if(en) {
			return "Add values for a sensor";
		}else {
			return "A\u00F1adir valores para un sensor";
		}
	}

	public static String addValuesShort() {
		if(de) {
			return "Werte hinzufügen";
		} else if(en) {
			return "Add values";
		} else {
			return "A\u00F1adir valores";
		}
	}

	public static String numberBetween(double low, double high) {
		if(de) {
			return "Bitte geben Sie eine Zahl zwischen "+low+" und "+high+" ein!";
		} else if(en) {
			return "Please enter a number between "+low+" and "+high+"!";
		} else {
			return "Por favor, ingrese un n\u00FAmero entre "+ low +" y "+ high +"! \"";
		}
	}

	public static String validNumber() {
		if(de) {
			return "Bitte geben Sie eine gültige Zahl ein!";
		} else if(en) {
			return "Please enter a valid number!";
		} else  {
			return "\u00A1Por favor ingrese un n\u00FAmero valido!";
		}
	}

	public static String cannotBeEmpty() {
		if(de) {
			return "Dieses Feld darf nicht leer sein";
		} else if(en) {
			return "This field cannot be empty";
		} else {
			return "Este campo no puede estar vac\u00EDo";
		}
	}

	public static String invalidPassword() {
		if(de) {
			return "Das Passwort muss mindestens 8 Zeichen, eine Zahl, einen Groß- und einen Kleinbuchstaben enthalten.";
		} else if(en) {
			return "The password must be at least 8 characters long and contain one or more numbers, lowercase and uppercase letters.";
		} else {
			return "La contrase\u00F1a debe contener al menos 8 caracteres, un n\u00FAmero, una letra may\u00FAscula y una letra min\u00FAscula.";
		}
	}

	public static String mapPageTourStep1() {
		if(de) {
			return "Wählen Sie hier aus einen der Listen Sensoren. Falls die Listen noch leer sind können Sie über die Suchseite sensoren hinzufügen."
					+ " Ausgewählte Sensoren werden dann auf der Karte angzeigt.";
		} else if(en) {
			return "Choose from one of the lists Sensors. If the lists are still empty, you can add sensors via the search page."
					+ "Selected sensors will then be displayed on the map.";
		} else {
			return "Elige entre una de las listas de Sensores. Si las listas siguen vac\u00EDas, puede agregar sensores a trav\u00E9s de la p\u00E1gina de b\u00FAsqueda."
					+ "Los sensores seleccionados se mostrar\u00E1n en el mapa.";
		}
	}

	public static String mapPageTourStep2() {
		if(de) {
			return "Drücken Sie auf einen Sensor um einen kurzen Überblick über diesen zu erhalten."
					+ "Wenn Sie viele Sensoren ausgewählt haben, dann werden diese zur besseren Übersicht in bunte Bündel gepackt. Klicken Sie"
					+ " die auf die Bündel um näher herein zu zoomen.";
		} else if(en) {
			return "Press on a sensor to get a brief overview of it. If you have selected many sensors, "
					+ "then these are packed into colorful bundles for a better overview."
					+ " Click on the bundles to zoom in. \"";
		} else {
			return "Presione sobre un sensor para obtener una breve descripci\u00F3n de \u00E9l. Si ha seleccionado muchos sensores,"
					+ " estos est\u00E1n empaquetados en coloridos paquetes para una mejor visi\u00F3n general."
					+ " Haga clic en los paquetes para ampliar.";
		}
	}
	
	public static String markerTip() {
		if(de) {
			return "Tipp: Sie können den Marker an eine andere Position ziehen oder dort doppelklicken, um ihn zu bewegen.";
		} else if(en) {
			return "Tip: You can drag the marker to a new position or double-click somewhere to move the marker there.";
		} else {
			return "";
		}
	}
	
	
	public static String alwaysShowTour() {
		if(de) {
			return "Rundgänge automatisch starten";
		} else if(en) {
			return "Start tours automatically";
		} else {
			return "Los recorridos siempre deben comenzar";
		}
	}

	public static String resetTourCookies() {
		if(de) {
			return "Rundgänge zurücksetzen";
		} else if(en) {
			return "Reset tours";
		} else {
			return "Restablecer";
		}
	}

	public static String recenterToBounds() {
		if(de) {
			return "Setzt die Karte zurück auf den Ursprung";
		} else if(en) {
			return "Recenters Map back to bounds";
		} else {
			return "Devuelve la carta al origen";
		}
	}
}
