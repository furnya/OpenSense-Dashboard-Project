package com.opensense.dashboard.client.utils;

import java.util.Date;

/**
 * The languages are set to german by default to change it use the setter
 * @author Roeber
 */
public class Languages {

	/**
	 * German or english
	 */
	private static boolean de = true;
	private static boolean en = false;

	private Languages() {
		// Empty private constructor to hide the implicit public one.
	}

	public static boolean isGerman() {
		return de;
	}

	public static boolean isEnglish() {
		return en;
	}

	public static void setGerman() {
		de = true;
		en = false;
	}

	public static void setEnglish() {
		de = false;
		en = true;
	}

	public static String getActualLanguageString() {
		if(de) {
			return "de";
		}else {
			return "en";
		}
	}

	public static String text() {
		if(de) {
			return "Das ist der text";
		}else {
			return "";
		}
	}

	public static String home() {
		if(de) {
			return "Zuhause";
		}else {
			return "Home";
		}
	}

	public static String search() {
		if(de) {
			return "Suche";
		}else {
			return "Search";
		}
	}

	public static String map() {
		if(de) {
			return "Karte";
		}else {
			return "Map";
		}
	}

	public static String graphics() {
		if(de) {
			return "Grafiken";
		}else {
			return "Visualizations";
		}
	}

	public static String list() {
		if(de) {
			return "Listen";
		}else {
			return "Lists";
		}
	}

	public static String user() {
		if(de) {
			return "Benutzerverwaltung";
		}else {
			return "User";
		}
	}

	public static String errorDataPanelPageLoading() {
		if(de) {
			return "Fehler beim laden der Seite";
		}else {
			return "Error while loading the page";
		}
	}

	public static String searchPlace() {
		if(de) {
			return "Sensoren Ortsabhängig suchen";
		}else {
			return "Search by place";
		}
	}

	public static String maxSensors() {
		if(de) {
			return "Maximale Anzahl von Sensoren";
		}else {
			return "Maximum amount of sensors";
		}
	}

	public static String minAccuracy() {
		if(de) {
			return "Minimale Genauigkeit";
		}else {
			return "Minimal accuracy";
		}
	}

	public static String maxAccuracy() {
		if(de) {
			return "Maximale Genauigkeit";
		}else {
			return "Maximal accuracy";
		}
	}

	public static String unit() {
		if(de) {
			return "Einheit";
		}else {
			return "Unit";
		}
	}

	public static String minValue() {
		if(de) {
			return "Minimaler Wert";
		}else {
			return "Minimal value";
		}
	}

	public static String maxValue() {
		if(de) {
			return "Maximaler Wert";
		}else {
			return "Maximal value";
		}
	}

	public static String minDate() {
		if(de) {
			return "Minimales Datum";
		}else {
			return "Minimal date";
		}
	}

	public static String maxDate() {
		if(de) {
			return "Maximales Datum";
		}else {
			return "Maximal date";
		}
	}

	public static String minTime() {
		if(de) {
			return "Minimale Zeit";
		}else {
			return "Minimal time";
		}
	}

	public static String maxTime() {
		if(de) {
			return "Maximale Zeit";
		}else {
			return "Minimale";
		}
	}

	public static String type() {
		if(de) {
			return "Typ";
		}else {
			return "Type";
		}
	}

	public static String measurand() {
		if(de) {
			return "Messgrösse";
		}else {
			return "Measurand";
		}
	}

	public static String all() {
		if(de) {
			return "Alle";
		}else {
			return "All";
		}
	}

	public static String languageChange() {
		if(de) {
			return "EN";
		}else {
			return "DE";
		}
	}

	public static String past24Hours() {
		if(de) {
			return "Letzte 24h";
		}else {
			return "Past 24h";
		}
	}

	public static String pastWeek() {
		if(de) {
			return "Letzte Woche";
		}else {
			return "Past week";
		}
	}

	public static String pastMonth() {
		if(de) {
			return "Letzter Monat";
		}else {
			return "Past month";
		}
	}

	public static String pastYear() {
		if(de) {
			return "Letztes Jahr";
		}else {
			return "Past year";
		}
	}

	public static String customRange() {
		if(de) {
			return "Benutzerdefiniert";
		}else {
			return "Custom range";
		}
	}

	public static String timespan() {
		if(de) {
			return "Zeitspanne";
		}else {
			return "Timespan";
		}
	}

	public static String from() {
		if(de) {
			return "Von";
		}else {
			return "From";
		}
	}

	public static String to() {
		if(de) {
			return "Bis";
		}else {
			return "To";
		}
	}

	public static String seletedShowOnMap() {
		if(de) {
			return "Auf Karte zeigen";
		}else {
			return "Show on map";
		}
	}

	public static String seletedShowOnSearch() {
		if(de) {
			return "In Suche zeigen";
		}else {
			return "Show on search";
		}
	}

	public static String selectedShowVisualisations() {
		if(de) {
			return "Werte anzeigen";
		}else {
			return "Show values";
		}
	}

	public static String selectedShowInSearch() {
		if(de) {
			return "In Suche anzeigen";
		}else {
			return "Show in search";
		}
	}

	public static String selectedAddToList() {
		if(de) {
			return "Zur Liste hinzufügen";
		}else {
			return "Add to list";
		}
	}

	public static String allSelectedSensors() {
		if(de) {
			return "Alle ausgewählten Sensoren:";
		}else {
			return "All selected sensors";
		}
	}

	public static String selectAllSensors() {
		if(de) {
			return "Alle auswählen";
		}else {
			return "Select all";
		}
	}

	public static String deselectAllSensors() {
		if(de) {
			return "Alle abwählen";
		}else {
			return "Deselect all";
		}
	}

	public static String backwards(){
		if(de){
			return "Zum Anfang";
		}else{
			return "Back to start";
		}
	}

	public static String forwards(){
		if(de){
			return "Zum Ende";
		}else{
			return "Forward to end";
		}
	}

	public static String forwardsStepByStep(){
		if(de){
			return "Nächste Seite";
		}else{
			return "Next page";
		}
	}

	public static String backwardsStepByStep(){
		if(de){
			return "Vorherige Seite";
		}else{
			return "Previous page";
		}
	}

	public static String setPageNumber(int page, int maxObjectsOnPage, int size) {
		if(de){
			if (size == 0) {
				return "0-0 von 0";
			}
			return Integer.toString((page * maxObjectsOnPage) + 1) + "-" + ((size > ((page + 1) * maxObjectsOnPage)) ? Integer.toString((page + 1) * maxObjectsOnPage) : size) + " von " + Integer.toString(size);
		}else{
			if (size == 0) {
				return "0-0 of 0";
			}
			return Integer.toString((page * maxObjectsOnPage) + 1) + "-" + ((size > ((page + 1) * maxObjectsOnPage)) ? Integer.toString((page + 1) * maxObjectsOnPage) : size) + " of " + Integer.toString(size);
		}
	}

	public static String pageConfiguration(){
		if(de){
			return "Anzahl von Objekten auf einer Seite";
		}else{
			return "Number of objects on one page";
		}
	}

	public static String noData(){
		if(de){
			return "Es konnten keine Daten zu den gewählten Suchparametern gefunden werden";
		}else{
			return "No data was found for the chosen parameters";
		}
	}

	public static String sensorId() {
		return "ID ";
	}

	@SuppressWarnings("deprecation")
	public static String getDate(Date timestamp) {
		return timestamp.toLocaleString();
	}

	public static String username(){
		if(de){
			return "Benutzername";
		}else{
			return "Username";
		}
	}

	public static String password(){
		if(de){
			return "Passwort";
		}else{
			return "Password";
		}
	}

	public static String passwordVerify(){
		if(de){
			return "Passwort bestätigen";
		}else{
			return "Confirm password";
		}
	}

	public static String firstValue() {
		if(de){
			return "Erste Messung";
		}else{
			return "First measurement";
		}
	}

	public static String lastValue() {
		if(de){
			return "Letzer Messung";
		}else{
			return "Last measurement";
		}
	}

	public static String noValuePreviewData() {
		if(de){
			return "Keine Messungen vorhanden";
		}else{
			return "No measurements available";
		}
	}

	public static String rating(double rating) {
		if(de){
			return "Genauigkeit: " + rating + " von 5 Sternen";
		}else{
			return "Accuracy: " + rating + " out of 5 stars";
		}
	}

	public static String altitudeAboveGround() {
		if(de) {
			return "Höhe über Grund:";
		}else {
			return "Altitude above ground:";
		}
	}

	public static String origin() {
		if(de) {
			return "Herkunft:";
		}else {
			return "Origin:";
		}
	}

	public static String register() {
		if(de) {
			return "Registrieren";
		}else {
			return "Register";
		}
	}

	public static String forgotPassword() {
		if(de) {
			return "Passwort vergessen?";
		}else {
			return "Forgot password?";
		}
	}

	public static String login() {
		if(de) {
			return "Einloggen";
		}else {
			return "Log in";
		}
	}

	public static String logout(){
		if(de){
			return "Ausloggen";
		}else{
			return "Log out";
		}
	}

	public static String loginValidateError() {
		if(de){
			return "Ungültige E-Mail-Adresse oder ungültiges Kennwort";
		}else{
			return "Not valid E-mail address or not valid passord";
		}
	}

	public static String accuracy() {
		if(de) {
			return "Genauigkeit";
		}else {
			return "Accuracy";
		}
	}

	public static String sensorTyp() {
		if(de) {
			return "Sensortyp";
		}else {
			return "Sensormodel";
		}
	}

	public static String noListData(){
		if(de){
			return "Es wurden noch keine Sensoren zur Liste hinzugefügt";
		}else{
			return "No sensors existing in this list";
		}
	}

	public static String createNewList(){
		if(de){
			return "Neue Liste erstellen";
		}else{
			return "Create new list";
		}
	}

	public static String newList() {
		if(de){
			return "Neue Liste ";
		}else{
			return "New list ";
		}
	}

	public static String favorites() {
		if(de){
			return "Favoriten";
		}else{
			return "Favorites";
		}
	}

	public static String selectedSensors() {
		if(de){
			return "Ausgewälte Sensoren";
		}else{
			return "Selected sensors";
		}
	}

	public static String mySensors() {
		if(de){
			return "Meine Sensoren";
		}else{
			return "My sensors";
		}
	}

	public static String createSensor() {
		if(de){
			return "Sensor erstellen";
		}else{
			return "Create sensor";
		}
	}

	public static String hideSensorWIithoutValues() {
		if(de){
			return "Sensoren ohne Werte ausblenden";
		}else{
			return "Sensors without values wont be visible";
		}
	}

	public static String license() {
		if(de){
			return "Lizenz";
		}else{
			return "License";
		}
	}

	public static String latitude() {
		if(de){
			return "Breitengrad";
		}else{
			return "Latitude";
		}
	}

	public static String longitude() {
		if(de){
			return "Längenengrad";
		}else{
			return "Longitude";
		}
	}

	public static String directionVertical() {
		if(de){
			return "Vertikale Ausrichtung";
		}else{
			return "Vertical direction";
		}
	}

	public static String directionHorizontal() {
		if(de){
			return "Horizontale Ausrichtung";
		}else{
			return "Horizontal direction";
		}
	}

	public static String sensorModel() {
		if(de){
			return "Sensor-Modell";
		}else{
			return "Sensor model";
		}
	}

	public static String attributionText() {
		if(de){
			return "Beschreibungstext";
		}else{
			return "Attribution text";
		}
	}

	public static String attributionURL() {
		if(de){
			return "URL";
		}else{
			return "Attribution URL";
		}
	}

	public static String confirm() {
		if(de){
			return "Bestätigen";
		}else{
			return "Confirm";
		}
	}

	public static String cancel() {
		if(de){
			return "Abbrechen";
		}else{
			return "Cancel";
		}
	}

	public static String notLoggedIn() {
		if(de){
			return "Nicht eingeloggt";
		}else{
			return "Not logged in";
		}
	}

	public static String sensorCreated() {
		if(de){
			return "Sensor erstellt";
		}else{
			return "Sensor created";
		}
	}

	public static String invalidParameters() {
		if(de){
			return "Ungültige Parameter";
		}else{
			return "Invalid parameters";
		}
	}

	public static String with() {
		if(de){
			return "mit";
		}else{
			return "with";
		}
	}

	public static String connectionError() {
		if(de){
			return "Es ist ein Fehler aufgetreten. Wiederholen Sie Ihre Anfrage oder versuchen Sie es zu einem späterem Zeitpunkt erneut.";
		}else{
			return "";
		}
	}

	public static String selectListItemError() {
		if(de){
			return "Es ist ein Fehler beim auswählen der Liste aufgetreten.";
		}else{
			return "";
		}
	}

	public static String maxSensorSelectedLimitExceeded(int maxSensors) {
		if(de){
			return "Sie können nicht mehr als " + maxSensors + " Sensoren auswählen.";
		}else{
			return "";
		}
	}

	public static String maxFavoriteSensorsReached(int maxSensors) {
		if(de){
			return "Die Favoriten dürfen maximal " + maxSensors + " enthalten.";
		}else{
			return "";
		}
	}

	public static String deleteSensors() {
		if(de){
			return "Sensoren löschen";
		}else{
			return "";
		}
	}
	
	public static String searchForPlace() {
		if(de) {
			return "Nach Ort suchen";
		}else {
			return "Search for place";
		}
	}
}
