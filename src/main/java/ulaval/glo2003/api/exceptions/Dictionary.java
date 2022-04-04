package ulaval.glo2003.api.exceptions;

public class Dictionary {
    public static String MISSING_PARAMETER_EXCEPTION_DESC = "un paramètre (URL, header, JSON, etc.) est manquant";
    public static String INVALID_PARAMETER_EXCEPTION_DESC = "un paramètre (URL, header, JSON, etc.) est invalide "
                                                                + "(vide, négatif, trop long. etc.)";
    public static String ITEM_NOT_FOUND_EXCEPTION_DESC = "un élément est introuvable (id invalide ou inexistant)";
    public static String NULL_REFERENCE_EXCEPTION_DESC = "un objet null a été passé en paramètre";
}
