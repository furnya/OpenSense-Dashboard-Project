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
			return "Es konnten keine Daten zu den gewählten Suchparametern gefunden werden";
		} else if (en) {
			return "No data was found for the chosen parameters";
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
			return "Letzer Messung";
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
			return "";
		} else {
			return "Ha ocurrido un error. Repita su solicitud o intente de nuevo m\u00E1s tarde";
		}
	}

	public static String selectListItemError() {
		if (de) {
			return "Es ist ein Fehler beim auswählen der Liste aufgetreten.";
		} else if (en) {
			return "";
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
			return "";
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
			return (moreThanOne ? "The sensors " + sensors + " were" : "The sensor " + sensors + " was")
					+ " added to the list \"" + listName + "\"";
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
			return "(Enthält " + sensorsSize + (sensorsSize > 1 ? " Sensoren" : " Sensor") + ", davon ausgewählt: "
					+ selectedSensorsSize + ")";
		} else if (en) {
			return "(Contains " + sensorsSize + (sensorsSize > 1 ? " sensors" : " sensor") + ", selected of these: "
					+ selectedSensorsSize + ")";
		} else {
			return "(Contiene " + sensorsSize + (sensorsSize > 1 ? " sensores" : " sensor")
					+ ", seleccionado de estos: " + selectedSensorsSize + ")";
		}
	}

	public static String errorMessageAccuracy() {
		if (de) {
			return "Bitte gebe eine Zahl zwischen 0 und 10 ein";
		} else if (en) {
			return "Please enter a number between 0 and 10";
		} else {
			return "Por favor ingrese un n\u00FAmero entre 0 y 10 ";
		}
	}

	public static String errorMessageMaxSens() {
		if (de) {
			return "Bitte gebe eine Zahl zwischen 1 und 19999 ein";
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
			return "";
		} else if (en) {
			return "";
		} else {
			return "";
		}
	}

	public static String searchInfoText() {
		if (de) {
			return "Auf dieser Seite kannst du Sensoren suchen und ihre Informationen einsehen."
					+ "Die Sensoren können parametrisiert sowie Ortsabhängig gesucht werden.";
		} else if (en) {
			return "On this page you can search the sensors.\r\n"
					+ "The sensors can be parameterized and searched for location-dependent.";
		} else {
			return "En esta p\u00E1gina puedes buscar los sensores.\r\n"
					+ "Los sensores pueden parametrizarse y buscarse seg\u00FAn la ubicaci\u00F3n.";
		}
	}

	public static String mapInfoText() {
		if (de) {
			return "Auf der von Google bereitgestellten Karte kannst du dir deine Sensoren anzeigen lassen."
					+ "Die Sensoren werden abhängig von ihrem Typ mit einem anderen Symbol auf der Karte angezeigt."
					+ "Sollte sich mehrere Sensoren auf einer Stelle befinden so wird dies durch dieses Symbol deutlich:";
		} else if (en) {
			return "Englischer text für MapPage";
		} else {
			return "Spanischer text für MapPage";
		}
	}

	public static String visuInfoText() {
		if (de) {
			return "Die hier bereitgestellte Funktionalität ermöglicht es dir, die Werte der Sensoren je nach belieben (Tag, Monat, Jahr, oder spezifischer), in einem Diagramm darzustellen."
					+ "Dabei behälst du dank der farblichen Markierung der Angezeigten Sensoren immer den Überblick."
					+ "Bitte beachte dabei, dass du maximal 10 Sensoren zur gleichen Zeit in den Listen auswählen kannst.";
		} else if (en) {
			return "Englischer text für VisuPage";
		} else {
			return "Spanischer text für VisuPage";
		}
	}

	public static String listInfoText() {
		if (de) {
			return "Damit du nicht immer wieder die Selbe Sensoren auf der Suchseite suchen musst, kannst du dir deine eigenen Listen erstellen."
					+ "Erstelle eine Liste und füge über die Suchseite passende Sensoren hinzu."
					+ "Außerdem kannst du, wenn du einen Entsprechenden Account besitzt auch einfach deinen/deine eigenen Sensor/-en zu OpenSense hinzufügen.";
		} else if (en) {
			return "Englischer Text für ListPage";
		} else {
			return "Spanischer text für ListPage";
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
		} else {
			return "While running the tour an error occues. Please try to start the tour again at the help page. If the error"
					+ " still occurs, please contact us!";
		}
	}

	public static String exitTourFirst() {
		if (de) {
			return "Zum beenden des Rundgangs";
		} else {
			return "Press";
		}
	}

	public static String exitTourSecond() {
		if (de) {
			return "drücken";
		} else {
			return "to exit the tour";
		}
	}

	public static String startTour() {
		if (de) {
			return "Tour starten";
		} else {
			return "Start tour";
		}
	}

	public static String continueStep() {
		if (de) {
			return "Weiter";
		} else {
			return "Continue";
		}
	}

	public static String noTourData() {
		if (de) {
			return "Keine Touren verfügbar";
		} else {
			return "No tours available";
		}
	}

	public static String closeTour() {
		if (de) {
			return "Tour beenden";
		} else {
			return "Exit tour";
		}
	}

	public static String dontShowTours() {
		if (de) {
			return "Keine Touren mehr anzeigen";
		} else {
			return "Do not show any tours";
		}
	}

	public static String tourHeader(int tourStep, int tourSteps) {
		if (de) {
			return "Rundgang: Schritt " + tourStep + " von " + tourSteps;
		} else {
			return "Tour: Step " + tourStep + " of " + tourSteps;
		}
	}

	public static String showTourOnPage() {
		if (de) {
			return "Touren automatisch anzeigen, wenn der Startpunkt aufgerufen wird";
		} else {
			return "Show tours automaticaly if you reaching the startpoint";
		}
	}

	public static String clearFinishedTours() {
		if (de) {
			return "Alle Rundgänge erneut starten";
		} else {
			return "Start all tours again";
		}
	}

	public static String infoTours() {
		if (de) {
			return "Hinweis: Rundgänge starten automatisch, wenn Sie das erste mal auf die Seite kommen. "
					+ "Wenn Sie alle Rundgänge erneut starten, bekommen Sie bereits beendete erneut angezeigt.";
		} else {
			return "";
		}
	}

	public static String measurandsTour() {
		if (de) {
			return "Wenn Sie nur nach Sensoren mit einer bestimmten Messgröße suchen wollen, können Sie diese in der Liste auswählen.";
		} else {
			return "";
		}
	}

	public static String placeInputTour() {
		if (de) {
			return "Sie können Sensoren ortbezogen suchen.";
		} else {
			return "";
		}
	}

	public static String maxSensorsTour() {
		if (de) {
			return "Geben Sie immer eine maximale Anzahl von Sensoren ein, sonst dauern Anfragen sehr lange. Wir empfehlen außerdem niemals mehr als 1000 Sensoren anzufragen, da sie bis zu 5 Minuten dauern kann."
					+ "Tragen Sie beispielweise 50 ein und drücken \"Weiter\".";
		} else {
			return "";
		}
	}

	public static String accuracyTour() {
		if (de) {
			return "Sie können eine Geauigkeit der Sensoren angeben auf einer Skala von 0-10. Die Genauigkeit wird Ihnen als Sternebewertung in den Sensoren angezeigt.";
		} else {
			return "";
		}
	}

	public static String searchButtonTour() {
		if (de) {
			return "Suchen Sie Sensoren anhand der gewählten Parameter indem Sie den Knopf betätigen.";
		} else {
			return "";
		}
	}

	public static String sensorsWithValuesTour() {
		if (de) {
			return "Wenn Sie Sensoren, welche keine Werte gesammelt haben ausschließen möchten, lassen Sie das Kontrollkästchen ausgewählt.";
		} else {
			return "";
		}
	}

	public static String createListButtonTour() {
		if (de) {
			return "Sie können eine neue Liste erstellen und sie beliebig benennen. Wenn Sie ihrer Liste dann Sensoren hinzufügen möchten, dann gehen Sie bitte auf die Suchseite und fügen dort die entsprechenden Sensoren ein.";
		} else if (en) {
			return "You have successfully registered";
		} else {
			return "Te has registrado exitosamente";
		}
	}

	public static String createSensorButtonTour() {
		if (de) {
			return "Sie können einen Sensor zu Opensense hinzufügen. Bitte achten Sie darauf ihre Daten vor dem Abschicken noch einmal zu Überprüfen. Ihre eigenen Sensoren können sie anschließend jeder Zeit hier auf der Suchseite einsehen und löschen.";
		} else if (en) {
			return "You have successfully registered";
		} else {
			return "Te has registrado exitosamente";
		}
	}

}
