package clarity.domain;

import clarity.util.LabelValue;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

public class AvetmissConstant {

    public final static String DELIVERY_LOCATION_IDENTIFIER_QUEEN_STREET = "CITY";

    public final static String TOID_VIT = "20829";
    public final static String TOID_RHODES = "21870";
    public static final String FUNDING_SOURCE_NATIONAL_INTERNATIONAL_FULL_FEE = "30";
    public static final String FUNDING_SOURCE_STATE_IDENTIFIER_OVERSEAS_FULL_FEE = "F";
    public static final String CONCESSION_TYPE_IDENTIFIER_NONE = "Z";

    public final static String DISABILITY_TYPE_IDENTIFIER_PHYSICAL = "12";
    public final static String DISABILITY_TYPE_IDENTIFIER_LEARNING = "14";
    public final static String DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED = "99";
    public final static String DISABILITY_TYPE_OTHER = "19";

    /**
     * A code that specifies the type of fee exemption or concession the client
     * has been granted for the program of study
     */
    public static List<LabelValue> getConcessionTypeIdentifiers() {
        Map<String, String> identifiers = newLinkedHashMap();
        identifiers.put("G", "VCE Scholarship");
        identifiers.put("H", "Health Care Card");
        identifiers.put("J", "Job Seeker and concession card holder (covered by a current, relevant pensioner Concession Card, Health Care Card or Veteran's Gold Card)");
        identifiers.put("K", "Job Seeker AND NOT currently holding a relevant Pensioner Concession Card, Health Care Card or Veteran's Gold Card");
        identifiers.put("M", "Prisoner");
        identifiers.put("O", "Other");
        identifiers.put("P", "Pensioner Concession Card");
        identifiers.put("V", "Veteran Gold Card Concession");
        identifiers.put(CONCESSION_TYPE_IDENTIFIER_NONE, "None");
        return AvetmissUtil.asLabelValue(identifiers);
    }

    public static final String INDIGENOUS_STATUS_IDENTIFIERS_NEITHER = "4";
    public static List<LabelValue> getIndigenousStatusIdentifiers() {
        List<LabelValue> indigenousStatusList = newArrayList();
        indigenousStatusList.add(new LabelValue("No", INDIGENOUS_STATUS_IDENTIFIERS_NEITHER, "No, neither Aboriginal nor Torres Strait Islander"));
        indigenousStatusList.add(new LabelValue("Yes, Aboriginal", "1"));
        indigenousStatusList.add(new LabelValue("Yes, Torres Strait Islander", "2"));
        indigenousStatusList.add(new LabelValue("Yes, Aboriginal AND Torres Strait Islander", "3"));
        indigenousStatusList.add(new LabelValue("Not Provided", "@"));
        return indigenousStatusList;
    }


    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_APPLICABLE = " ";
    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_PROVIDED = "@";
    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_VERY_WELL = "1";
    public static List<LabelValue> getEnglishProficiencyIdentifiers() {
        List<LabelValue> proficiencyLevels = newArrayList();
        proficiencyLevels.add(new LabelValue("Very well", ENGLISH_PROFICIENCY_IDENTIFIERS_VERY_WELL));
        proficiencyLevels.add(new LabelValue("Well", "2"));
        proficiencyLevels.add(new LabelValue("Not well", "3"));
        proficiencyLevels.add(new LabelValue("Not at all", "4"));
        // proficiencyLevels.add(new LabelValue("Not Applicable - English is my main language", ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_APPLICABLE));
        proficiencyLevels.add(new LabelValue("Not Provided", ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_PROVIDED));
        return proficiencyLevels;
    }

    public static final String STATE_IDENTIFIERS_VIC = "02";
    public static final String STATE_IDENTIFIERS_OVERSEAS = "99";

    /**
     * Returns a list of Australian State names and their correspondent avatmiss
     * code.
     */
    public static List<LabelValue> getStateIdentifiers() {
        Map<String, String> identifiers = newLinkedHashMap();
        identifiers.put(STATE_IDENTIFIERS_VIC, "VIC");
        identifiers.put("01", "NSW");
        identifiers.put("04", "SA");
        identifiers.put("03", "QLD");
        identifiers.put("07", "NT");
        identifiers.put("05", "WA");
        identifiers.put("08", "ACT");
        identifiers.put("06", "TAS");
        // we probably don't need this
        // identifiers.put("09", "Other Australian Territories or Dependencies");
        identifiers.put(STATE_IDENTIFIERS_OVERSEAS, "Overseas");

        return AvetmissUtil.asLabelValue(identifiers);
    }

    public static List<LabelValue> getLevelOfEducationIdentifier() {
        Map<String, String> identifiers = newLinkedHashMap();

        identifiers.put("211", "Graduate Diploma");
        identifiers.put("213", "Professional Specialist Qualification at Graduate Diploma Level");
        identifiers.put("221", "Graduate Certificate");
        identifiers.put("222", "Professional Specialist Qualification at Graduate Certificate Level");
        identifiers.put("311", "Bachelor Degree (Honours)");
        identifiers.put("312", "Bachelor Degree (Pass)");
        identifiers.put("411", "Advanced Diploma");
        identifiers.put("413", "Associate Degree");
        identifiers.put("421", "Diploma");
        identifiers.put("511", "Certificate IV");
        identifiers.put("514", "Certificate III");
        identifiers.put("521", "Certificate II");
        identifiers.put("524", "Certificate I");
        identifiers.put("611", "Year 12");
        identifiers.put("613", "Year 11");
        identifiers.put("621", "Year 10");
        identifiers.put("912", "Other Non-award Courses");
        identifiers.put("991", "Statement of Attainment Not Identifiable by Level");
        identifiers.put("992", "Bridging and Enabling Courses Not Identifiable by Level");
        identifiers.put("999", "Education not elsewhere classified");

        return AvetmissUtil.asLabelValue(identifiers);
    }

    public static List<LabelValue> getDisabilityTypeIdentifiers() {
        Map<String, String> identifiers = newLinkedHashMap();

        identifiers.put("11", "Hearing/Deaf");
        identifiers.put(DISABILITY_TYPE_IDENTIFIER_PHYSICAL, "Physical");
        identifiers.put("13", "Intellectual");
        identifiers.put(DISABILITY_TYPE_IDENTIFIER_LEARNING, "Learning");
        identifiers.put("15", "Mental Illness");
        identifiers.put("16", "Acquired Brain Impairment");
        identifiers.put("17", "Vision");
        identifiers.put("18", "Medical Condition");
        identifiers.put(DISABILITY_TYPE_OTHER, "Other");
        identifiers.put(DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED, "Not Specified");

        return AvetmissUtil.asLabelValue(identifiers);
    }

    public static List<LabelValue> getLanguageIdentifiers() {
        Map<String, String> identifiers = newLinkedHashMap();

        identifiers.put("8998", "Aboriginal English, so described");
        identifiers.put("6513", "Acehnese");
        identifiers.put("9201", "Acholi");
        identifiers.put("8901", "Adnymathanha");
        identifiers.put("9200", "African Languages");
        identifiers.put("1403", "Afrikaans");
        identifiers.put("9203", "Akan");
        identifiers.put("8121", "Alawa");
        identifiers.put("3901", "Albanian");
        identifiers.put("8603", "Alyawarr");
        identifiers.put("9101", "American Languages");
        identifiers.put("9214", "Amharic");
        identifiers.put("8101", "Anindilyakwa");
        identifiers.put("8604", "Anmatyerr");
        identifiers.put("8703", "Antikarinya");
        identifiers.put("8902", "Arabana");
        identifiers.put("4202", "Arabic");
        identifiers.put("8600", "Arandic");
        identifiers.put("4901", "Armenian");
        identifiers.put("3903", "Aromunian (Macedo-Romanian)");
        identifiers.put("8605", "Arrernte");
        identifiers.put("5213", "Assamese");
        identifiers.put("4203", "Assyrian");
        identifiers.put("9701", "Auslan");
        identifiers.put("8000", "AUSTRALIAN INDIGENOUS LANGUAGES");
        identifiers.put("4302", "Azeri");
        identifiers.put("6514", "Balinese");
        identifiers.put("4104", "Balochi");
        identifiers.put("3100", "Baltic");
        identifiers.put("8903", "Bandjalang");
        identifiers.put("8904", "Banyjima");
        identifiers.put("8801", "Bardi");
        identifiers.put("2901", "Basque");
        identifiers.put("8905", "Batjala");
        identifiers.put("3401", "Belorussian");
        identifiers.put("9215", "Bemba");
        identifiers.put("5201", "Bengali");
        identifiers.put("8906", "Bidjara");
        identifiers.put("6515", "Bikol");
        identifiers.put("8504", "Bilinarra");
        identifiers.put("6501", "Bisaya");
        identifiers.put("9402", "Bislama");
        identifiers.put("3501", "Bosnian");
        identifiers.put("3502", "Bulgarian");
        identifiers.put("8802", "Bunuba");
        identifiers.put("8102", "Burarra");
        identifiers.put("6101", "Burmese");
        identifiers.put("7101", "Cantonese");
        identifiers.put("8300", "Cape York Peninsula Languages");
        identifiers.put("2301", "Catalan");
        identifiers.put("6502", "Cebuano");
        identifiers.put("1100", "Celtic");
        identifiers.put("7100", "Chinese");
        identifiers.put("0005", "Creole");
        identifiers.put("3503", "Croatian");
        identifiers.put("3601", "Czech");
        identifiers.put("8233", "Daatiwuy");
        identifiers.put("8122", "Dalabon");
        identifiers.put("1501", "Danish");
        identifiers.put("4105", "Dari");
        identifiers.put("8221", "Dhalwangu");
        identifiers.put("8907", "Dhanggatti");
        identifiers.put("8210", "Dhangu");
        identifiers.put("8220", "Dhay'yi");
        identifiers.put("5214", "Dhivehi");
        identifiers.put("8230", "Dhuwal");
        identifiers.put("8240", "Dhuwala");
        identifiers.put("8241", "Dhuwaya");
        identifiers.put("9216", "Dinka");
        identifiers.put("8908", "Diyari");
        identifiers.put("8305", "Djabugay");
        identifiers.put("8231", "Djambarrpuyngu");
        identifiers.put("8232", "Djapu");
        identifiers.put("8222", "Djarrwark");
        identifiers.put("8250", "Djinang");
        identifiers.put("8260", "Djinba");
        identifiers.put("5100", "Dravidian");
        identifiers.put("1401", "Dutch");
        identifiers.put("8306", "Dyirbal");
        identifiers.put("3400", "East Slavic");
        identifiers.put("7000", "EASTERN ASIAN LANGUAGES");
        identifiers.put("3000", "EASTERN EUROPEAN LANGUAGES");
        identifiers.put("1201", "English");
        identifiers.put("1601", "Estonian");
        identifiers.put("9217", "Ewe");
        identifiers.put("9301", "Fijian");
        identifiers.put("6512", "Filipino");
        identifiers.put("1602", "Finnish");
        identifiers.put("2101", "French");
        identifiers.put("0006", "French Creole");
        identifiers.put("1402", "Frisian");
        identifiers.put("9218", "Ga");
        identifiers.put("1101", "Gaelic (Scotland)");
        identifiers.put("8211", "Galpu");
        identifiers.put("8911", "Gamilaraay");
        identifiers.put("8261", "Ganalbingu");
        identifiers.put("8912", "Garrwa");
        identifiers.put("8913", "Garuwali");
        identifiers.put("4902", "Georgian");
        identifiers.put("1301", "German");
        identifiers.put("9302", "Gilbertese");
        identifiers.put("8307", "Girramay");
        identifiers.put("8914", "Githabul");
        identifiers.put("8212", "Golumala");
        identifiers.put("8803", "Gooniyandi");
        identifiers.put("2201", "Greek");
        identifiers.put("8123", "Gudanji");
        identifiers.put("5202", "Gujarati");
        identifiers.put("8242", "Gumatj");
        identifiers.put("8915", "Gumbaynggir");
        identifiers.put("8125", "Gun-nartpa");
        identifiers.put("8124", "Gundjeihmi");
        identifiers.put("8243", "Gupapuyngu");
        identifiers.put("8505", "Gurindji");
        identifiers.put("8506", "Gurindji Kriol");
        identifiers.put("8126", "Gurr-goni");
        identifiers.put("8302", "Guugu Yimidhirr");
        identifiers.put("8244", "Guyamirrilili");
        identifiers.put("6102", "Haka");
        identifiers.put("7102", "Hakka");
        identifiers.put("9221", "Harari");
        identifiers.put("9222", "Hausa");
        identifiers.put("9403", "Hawaiian English");
        identifiers.put("4204", "Hebrew");
        identifiers.put("5203", "Hindi");
        identifiers.put("6201", "Hmong");
        identifiers.put("6200", "Hmong-Mien");
        identifiers.put("7103", "Hokkien");
        identifiers.put("3301", "Hungarian");
        identifiers.put("6516", "Iban");
        identifiers.put("2300", "Iberian Romance");
        identifiers.put("1502", "Icelandic");
        identifiers.put("9223", "Igbo");
        identifiers.put("6503", "IIokano");
        identifiers.put("5200", "Indo-Aryan");
        identifiers.put("6504", "Indonesian");
        identifiers.put("9601", "Invented Languages");
        identifiers.put("4100", "Iranic");
        identifiers.put("1102", "Irish");
        identifiers.put("2401", "Italian");
        identifiers.put("8127", "Iwaidja");
        identifiers.put("8128", "Jaminjung");
        identifiers.put("7201", "Japanese");
        identifiers.put("8507", "Jaru");
        identifiers.put("6518", "Javanese");
        identifiers.put("8131", "Jawoyn");
        identifiers.put("8132", "Jingulu");
        identifiers.put("8916", "Kanai");
        identifiers.put("5101", "Kannada");
        identifiers.put("8917", "Karajarri");
        identifiers.put("6103", "Karen");
        identifiers.put("8918", "Kariyarra");
        identifiers.put("8704", "Kartujarra");
        identifiers.put("5215", "Kashmiri");
        identifiers.put("8921", "Kaurna");
        identifiers.put("8922", "Kayardild");
        identifiers.put("8606", "Kaytetye");
        identifiers.put("6301", "Khmer");
        identifiers.put("8923", "Kija");
        identifiers.put("9224", "Kikuyu");
        identifiers.put("8800", "Kimberley Area Languages");
        identifiers.put("9502", "Kiwai");
        identifiers.put("8308", "Koko-Bera");
        identifiers.put("5204", "Konkani");
        identifiers.put("7301", "Korean");
        identifiers.put("9225", "Krio");
        identifiers.put("8924", "Kriol");
        identifiers.put("8705", "Kukatha");
        identifiers.put("8706", "Kukatja");
        identifiers.put("8301", "Kuku Yalanji");
        identifiers.put("8133", "Kunbarlang");
        identifiers.put("8134", "Kune");
        identifiers.put("8135", "Kuninjku");
        identifiers.put("8108", "Kunwinjku");
        identifiers.put("4101", "Kurdish");
        identifiers.put("8311", "Kuuk Thayorre");
        identifiers.put("8303", "Kuuku-Ya'u");
        identifiers.put("8312", "Lamalama");
        identifiers.put("6401", "Lao");
        identifiers.put("8925", "Lardil");
        identifiers.put("8136", "Larrakiya");
        identifiers.put("2902", "Latin");
        identifiers.put("3101", "Latvian");
        identifiers.put("1302", "Letzeburgish");
        identifiers.put("8508", "Light Warlpiri");
        identifiers.put("3102", "Lithuanian");
        identifiers.put("8235", "Liyagalawumirr");
        identifiers.put("9226", "Luganda");
        identifiers.put("9227", "Luo");
        identifiers.put("8707", "Luritja");
        identifiers.put("3504", "Macedonian");
        identifiers.put("8245", "Madarrpa");
        identifiers.put("9702", "Makaton");
        identifiers.put("8137", "Malak Malak");
        identifiers.put("6505", "Malay");
        identifiers.put("5102", "Malayalam");
        identifiers.put("8511", "Malngin");
        identifiers.put("2501", "Maltese");
        identifiers.put("7104", "Mandarin");
        identifiers.put("8926", "Mangala");
        identifiers.put("8138", "Mangarrayi");
        identifiers.put("8246", "Manggalili");
        identifiers.put("8708", "Manyjilyjarra");
        identifiers.put("9303", "Maori (Cook Island)");
        identifiers.put("9304", "Maori (New Zealand)");
        identifiers.put("5205", "Marathi");
        identifiers.put("8141", "Maringarr");
        identifiers.put("8142", "Marra");
        identifiers.put("8234", "Marrangu");
        identifiers.put("8143", "Marrithiyel");
        identifiers.put("8711", "Martu Wangka");
        identifiers.put("8144", "Matngala");
        identifiers.put("8111", "Maung");
        identifiers.put("9205", "Mauritian Creole");
        identifiers.put("8145", "Mayali");
        identifiers.put("8402", "Meriam Mir");
        identifiers.put("4200", "Middle Eastern Semitic Languages");
        identifiers.put("8804", "Miriwoong");
        identifiers.put("6303", "Mon");
        identifiers.put("6300", "Mon-Khmer");
        identifiers.put("7902", "Mongolian");
        identifiers.put("9305", "Motu");
        identifiers.put("8512", "Mudburra");
        identifiers.put("8146", "Murrinh Patha");
        identifiers.put("8927", "Muruwari");
        identifiers.put("8147", "Na-kara");
        identifiers.put("8928", "Narungga");
        identifiers.put("9306", "Nauruan");
        identifiers.put("9228", "Ndebele");
        identifiers.put("5206", "Nepali");
        identifiers.put("8712", "Ngaanyatjarra");
        identifiers.put("8151", "Ngalakgan");
        identifiers.put("8152", "Ngaliwurru");
        identifiers.put("8113", "Ngan'gikurunggurr");
        identifiers.put("8513", "Ngandi");
        identifiers.put("8514", "Ngardi");
        identifiers.put("8805", "Ngarinyin");
        identifiers.put("8515", "Ngarinyman");
        identifiers.put("8931", "Ngarluma");
        identifiers.put("8932", "Ngarrindjeri");
        identifiers.put("8280", "Nhangu");
        identifiers.put("8281", "Nhangu");
        identifiers.put("9307", "Niue");
        identifiers.put("0001", "Non Verbal");
        identifiers.put("8500", "Northern Desert Fringe Area Languages");
        identifiers.put("1000", "NORTHERN EUROPEAN LANGUAGES");
        identifiers.put("1503", "Norwegian");
        identifiers.put("@@@@", "Not Stated");
        identifiers.put("9231", "Nuer");
        identifiers.put("8153", "Nungali");
        identifiers.put("8114", "Nunggubuyu");
        identifiers.put("8933", "Nyamal");
        identifiers.put("8934", "Nyangumarta");
        identifiers.put("9232", "Nyanja (Chichewa)");
        identifiers.put("8806", "Nyikina");
        identifiers.put("8935", "Nyungar");
        identifiers.put("9400", "Oceanian Pidgins and Creoles");
        identifiers.put("5216", "Oriya");
        identifiers.put("9206", "Oromo");
        identifiers.put("8900", "Other Australian Indigenous Languages");
        identifiers.put("7900", "Other Eastern Asian Languages");
        identifiers.put("3900", "Other Eastern European Languages");
        identifiers.put("9000", "OTHER LANGUAGES");
        identifiers.put("6999", "Other Southeast Asian Languages");
        identifiers.put("5999", "Other Southern Asian Languages");
        identifiers.put("2900", "Other Southern European Languages");
        identifiers.put("4900", "Other Southwest and Central Asian Languages");
        identifiers.put("8290", "Other Yolngu Matha");
        identifiers.put("8936", "Paakantyi");
        identifiers.put("9300", "Pacific Austronesian Languages");
        identifiers.put("8937", "Palyku/Nyiyaparli");
        identifiers.put("6521", "Pampangan");
        identifiers.put("9500", "Papua New Guinea Papuan Languages");
        identifiers.put("4102", "Pashto");
        identifiers.put("4106", "Persian (excluding Dari)");
        identifiers.put("0009", "Pidgin");
        identifiers.put("8713", "Pintupi");
        identifiers.put("9404", "Pitcairnese");
        identifiers.put("8714", "Pitjantjatjara");
        identifiers.put("3602", "Polish");
        identifiers.put("2302", "Portuguese");
        identifiers.put("0008", "Portuguese Creole");
        identifiers.put("5207", "Punjabi");
        identifiers.put("8115", "Rembarrnga");
        identifiers.put("8271", "Ritharrngu");
        identifiers.put("3904", "Romanian");
        identifiers.put("3905", "Romany");
        identifiers.put("9312", "Rotuman");
        identifiers.put("3402", "Russian");
        identifiers.put("9308", "Samoan");
        identifiers.put("1500", "Scandinavian");
        identifiers.put("3505", "Serbian");
        identifiers.put("9238", "Seychelles Creole");
        identifiers.put("9233", "Shilluk");
        identifiers.put("9207", "Shona");
        identifiers.put("9700", "Sign Languages");
        identifiers.put("5208", "Sindhi");
        identifiers.put("5211", "Sinhalese");
        identifiers.put("3603", "Slovak");
        identifiers.put("3506", "Slovene");
        identifiers.put("9405", "Solomon Islands Pijin");
        identifiers.put("9208", "Somali");
        identifiers.put("3500", "South Slavic");
        identifiers.put("6500", "Southeast Asian Austronesian Languages");
        identifiers.put("6000", "SOUTHEAST ASIAN LANGUAGES");
        identifiers.put("5000", "SOUTHERN ASIAN LANGUAGES");
        identifiers.put("2000", "SOUTHERN EUROPEAN LANGUAGES");
        identifiers.put("4000", "SOUTHWEST AND CENTRAL ASIAN LANGUAGES");
        identifiers.put("2303", "Spanish");
        identifiers.put("0007", "Spanish Creole");
        identifiers.put("9211", "Swahili");
        identifiers.put("1504", "Swedish");
        identifiers.put("0003", "Swiss, so described");
        identifiers.put("6511", "Tagalog");
        identifiers.put("6400", "Tai");
        identifiers.put("5103", "Tamil");
        identifiers.put("4303", "Tatar");
        identifiers.put("5104", "Telugu");
        identifiers.put("7105", "Teochew");
        identifiers.put("6507", "Tetum");
        identifiers.put("6402", "Thai");
        identifiers.put("7901", "Tibetan");
        identifiers.put("9235", "Tigrinya");
        identifiers.put("9234", "Tigr'");
        identifiers.put("6508", "Timorese");
        identifiers.put("8117", "Tiwi");
        identifiers.put("9401", "Tok Pisin");
        identifiers.put("9313", "Tokelauan");
        identifiers.put("9311", "Tongan");
        identifiers.put("8403", "Torres Strait Creole");
        identifiers.put("8400", "Torres Strait Island Languages");
        identifiers.put("9236", "Tswana");
        identifiers.put("5105", "Tulu");
        identifiers.put("4300", "Turkic");
        identifiers.put("4301", "Turkish");
        identifiers.put("4304", "Turkmen");
        identifiers.put("9314", "Tuvaluan");
        identifiers.put("3403", "Ukrainian");
        identifiers.put("0000", "Unknown"); // 0000 Inadequately described
        identifiers.put("5212", "Urdu");
        identifiers.put("4305", "Uygur");
        identifiers.put("4306", "Uzbek");
        identifiers.put("6302", "Vietnamese");
        identifiers.put("8938", "Wajarri");
        identifiers.put("8516", "Walmajarri");
        identifiers.put("8154", "Wambaya");
        identifiers.put("8715", "Wangkajunga");
        identifiers.put("8716", "Wangkatha");
        identifiers.put("8213", "Wangurri");
        identifiers.put("8517", "Wanyjirra");
        identifiers.put("8155", "Wardaman");
        identifiers.put("8518", "Warlmanpa");
        identifiers.put("8521", "Warlpiri");
        identifiers.put("8717", "Warnman");
        identifiers.put("8522", "Warumungu");
        identifiers.put("1103", "Welsh");
        identifiers.put("3600", "West Slavic");
        identifiers.put("8700", "Western Desert Language");
        identifiers.put("8304", "Wik Mungkan");
        identifiers.put("8314", "Wik Ngathan");
        identifiers.put("8941", "Wiradjuri");
        identifiers.put("8807", "Worla");
        identifiers.put("8808", "Worrorra");
        identifiers.put("7106", "Wu");
        identifiers.put("8247", "Wubulkarra");
        identifiers.put("8811", "Wunambal");
        identifiers.put("8251", "Wurlaki");
        identifiers.put("9237", "Xhosa");
        identifiers.put("8270", "Yakuy");
        identifiers.put("8718", "Yankunytjatjara");
        identifiers.put("8942", "Yanyuwa");
        identifiers.put("9315", "Yapese");
        identifiers.put("8812", "Yawuru");
        identifiers.put("1303", "Yiddish");
        identifiers.put("8313", "Yidiny");
        identifiers.put("8943", "Yindjibarndi");
        identifiers.put("8944", "Yinhawangka");
        identifiers.put("8200", "Yolngu Matha");
        identifiers.put("8945", "Yorta Yorta");
        identifiers.put("9212", "Yoruba");
        identifiers.put("8721", "Yulparija");
        identifiers.put("9213", "Zulu");

        List<LabelValue> languageIdentifiers = AvetmissUtil.asLabelValue(identifiers);
        Collections.sort(languageIdentifiers);
        return languageIdentifiers;
    }

    public static final String ANZSIC_CODES_NOT_APPLICABLE = "";
    public static List<LabelValue> getAnzsicCodes() {
        List<LabelValue> identifiers = Lists.newArrayList();
        identifiers.add(new LabelValue("01 Agriculture", "01"));
        identifiers.add(new LabelValue("02 Aquaculture", "02"));
        identifiers.add(new LabelValue("03 Forestry and Logging", "03"));
        identifiers.add(new LabelValue("04 Fishing, Hunting and Trapping", "04"));
        identifiers.add(new LabelValue("05 Agriculture, Forestry and Fishing Support Services", "05"));
        identifiers.add(new LabelValue("06 Coal Mining", "06"));
        identifiers.add(new LabelValue("07 Oil and Gas Extraction", "07"));
        identifiers.add(new LabelValue("08 Metal Ore Mining", "08"));
        identifiers.add(new LabelValue("09 Non-Metallic Mineral Mining and Quarrying", "09"));
        identifiers.add(new LabelValue("10 Exploration and Other Mining Support Services", "10"));
        identifiers.add(new LabelValue("11 Food Product Manufacturing", "11"));
        identifiers.add(new LabelValue("12 Beverage and Tobacco Product Manufacturing", "12"));
        identifiers.add(new LabelValue("13 Textile, Leather, Clothing and Footwear Manufacturing", "13"));
        identifiers.add(new LabelValue("14 Wood Product Manufacturing", "14"));
        identifiers.add(new LabelValue("15 Pulp, Paper and Converted Paper Product Manufacturing", "15"));
        identifiers.add(new LabelValue("16 Printing (including the Reproduction of Recorded Media)", "16"));
        identifiers.add(new LabelValue("17 Petroleum and Coal Product Manufacturing", "17"));
        identifiers.add(new LabelValue("18 Basic Chemical and Chemical Product Manufacturing", "18"));
        identifiers.add(new LabelValue("19 Polymer Product and Rubber Product Manufacturing", "19"));
        identifiers.add(new LabelValue("20 Non-Metallic Mineral Product Manufacturing", "20"));
        identifiers.add(new LabelValue("21 Primary Metal and Metal Product Manufacturing", "21"));
        identifiers.add(new LabelValue("22 Fabricated Metal Product Manufacturing", "22"));
        identifiers.add(new LabelValue("23 Transport Equipment Manufacturing", "23"));
        identifiers.add(new LabelValue("24 Machinery and Equipment Manufacturing", "24"));
        identifiers.add(new LabelValue("25 Furniture and Other Manufacturing", "25"));
        identifiers.add(new LabelValue("26 Electricity Supply", "26"));
        identifiers.add(new LabelValue("27 Gas Supply", "27"));
        identifiers.add(new LabelValue("28 Water Supply, Sewerage and Drainage Services", "28"));
        identifiers.add(new LabelValue("29 Waste Collection, Treatment and Disposal Services", "29"));
        identifiers.add(new LabelValue("30 Building Construction", "30"));
        identifiers.add(new LabelValue("31 Heavy and Civil Engineering Construction", "31"));
        identifiers.add(new LabelValue("32 Construction Services", "32"));
        identifiers.add(new LabelValue("33 Basic Material Wholesaling", "33"));
        identifiers.add(new LabelValue("34 Machinery and Equipment Wholesaling", "34"));
        identifiers.add(new LabelValue("35 Motor Vehicle and Motor Vehicle Parts Wholesaling", "35"));
        identifiers.add(new LabelValue("36 Grocery, Liquor and Tobacco Product Wholesaling", "36"));
        identifiers.add(new LabelValue("37 Other Goods Wholesaling", "37"));
        identifiers.add(new LabelValue("38 Commission-Based Wholesaling", "38"));
        identifiers.add(new LabelValue("39 Motor Vehicle and Motor Vehicle Parts Retailing", "39"));
        identifiers.add(new LabelValue("40 Fuel Retailing", "40"));
        identifiers.add(new LabelValue("41 Food Retailing", "41"));
        identifiers.add(new LabelValue("42 Other Store-Based Retailing", "42"));
        identifiers.add(new LabelValue("43 Non-Store Retailing and Retail Commission-Based Buying and/or Selling", "43"));
        identifiers.add(new LabelValue("44 Accommodation", "44"));
        identifiers.add(new LabelValue("45 Food and Beverage Services", "45"));
        identifiers.add(new LabelValue("46 Road Transport", "46"));
        identifiers.add(new LabelValue("47 Rail Transport", "47"));
        identifiers.add(new LabelValue("48 Water Transport", "48"));
        identifiers.add(new LabelValue("49 Air and Space Transport", "49"));
        identifiers.add(new LabelValue("50 Other Transport", "50"));
        identifiers.add(new LabelValue("51 Postal and Courier Pick-up and Delivery Services", "51"));
        identifiers.add(new LabelValue("52 Transport Support Services", "52"));
        identifiers.add(new LabelValue("53 Warehousing and Storage Services", "53"));
        identifiers.add(new LabelValue("54 Publishing (except Internet and Music Publishing)", "54"));
        identifiers.add(new LabelValue("55 Motion Picture and Sound Recording Activities", "55"));
        identifiers.add(new LabelValue("56 Broadcasting (except Internet)", "56"));
        identifiers.add(new LabelValue("57 Internet Publishing and Broadcasting", "57"));
        identifiers.add(new LabelValue("58 Telecommunications Services", "58"));
        identifiers.add(new LabelValue("59 Internet Service Providers, Web Search Portals and Data Processing Services", "59"));
        identifiers.add(new LabelValue("60 Library and Other Information Services", "60"));
        identifiers.add(new LabelValue("62 Finance", "62"));
        identifiers.add(new LabelValue("63 Insurance and Superannuation Funds", "63"));
        identifiers.add(new LabelValue("64 Auxiliary Finance and Insurance Services", "64"));
        identifiers.add(new LabelValue("66 Rental and Hiring Services (except Real Estate)", "66"));
        identifiers.add(new LabelValue("67 Property Operators and Real Estate Services", "67"));
        identifiers.add(new LabelValue("69 Professional, Scientific and Technical Services (Except Computer System Design and Related Services)", "69"));
        identifiers.add(new LabelValue("70 Computer System Design and Related Services", "70"));
        identifiers.add(new LabelValue("72 Administrative Services", "72"));
        identifiers.add(new LabelValue("73 Building Cleaning, Pest Control and Other Support Services", "73"));
        identifiers.add(new LabelValue("75 Public Administration", "75"));
        identifiers.add(new LabelValue("76 Defence", "76"));
        identifiers.add(new LabelValue("77 Public Order, Safety and Regulatory Services", "77"));
        identifiers.add(new LabelValue("80 Preschool and School Education", "80"));
        identifiers.add(new LabelValue("81 Tertiary Education", "81"));
        identifiers.add(new LabelValue("82 Adult, Community and Other Education", "82"));
        identifiers.add(new LabelValue("84 Hospitals", "84"));
        identifiers.add(new LabelValue("85 Medical and Other Health Care Services", "85"));
        identifiers.add(new LabelValue("86 Residential Care Services", "86"));
        identifiers.add(new LabelValue("87 Social Assistance Services", "87"));
        identifiers.add(new LabelValue("89 Heritage Activities", "89"));
        identifiers.add(new LabelValue("90 Creative and Performing Arts Activities", "90"));
        identifiers.add(new LabelValue("91 Sports and Recreation Activities", "91"));
        identifiers.add(new LabelValue("92 Gambling Activities", "92"));
        identifiers.add(new LabelValue("94 Repair and Maintenance", "94"));
        identifiers.add(new LabelValue("95 Personal and Other Services", "95"));
        identifiers.add(new LabelValue("96 Private Households Employing Staff and Undifferentiated Goods- and Service-Producing Activities of Households for Own Use", "96"));
        return identifiers;
    }

    public static final String FUNDING_SOURCE_NATIONAL_IDENTIFIER_INTERNATIONAL_FULL_FEE_PAYING = "30";
    public static final String FUNDING_SOURCE_STATE_IDENTIFIER_OVERSEAS_FEE_FOR_SERVICE = "F";


    public static List<LabelValue> getFundingSourceStateIdentifiers_GovernmentFunded() {
        return newArrayList(
                new LabelValue(
                        "P - Diploma and above (not Apprentice/Trainee)",
                        "P",
                        "General training delivery (non-Apprentice/Trainee training)"),
                new LabelValue(
                        "L - Diploma and above (Apprentice/Trainee)",
                        "L",
                        "Apprentice/Trainee. Training activity funded by Skills Victoria where the student is a registered apprentice/trainee."),
                new LabelValue(
                        "PSG - Skills for Growth - (not Apprentice/Trainee)",
                        "PSG",
                        "Skills for Growth - General (not Apprentice/Trainee).Training delivery to non-Apprentice/Trainees through Skills for Growth funding"),
                new LabelValue(
                        "LSG - Skills for Growth - (Apprentice/Trainee)",
                        "LSG",
                        "Skills for Growth - Apprentice/Trainee. Training delivery to apprentices and trainees through Skills for Growth funding"),
                new LabelValue(
                        "YRP - 19 to 24 Foundation or Cert I-IV and upskilling (not Apprentice/Trainee)",
                        "YRP",
                        "Youth Compact ' General (not Apprentice/Trainee)  (non-TAFE RTO only). Training delivery to eligible young people aged 15-24 as at 1 January of year of course commencement"),
                new LabelValue(
                        "Youth Compact - Apprentice/Trainee (non-TAFE RTOs only)",
                        "YRL",
                        "Youth Compact - Apprentice/Trainee (non-TAFE RTO only). Training delivery to eligible young people aged 15-24 as at 1 January of year of program (course) commencement. Program (course) enrolment commenced in 2010 and is continuing in 2015"),
                new LabelValue(
                        "RWP - Retrenched Worker 25 years and over, Cert I-IV, upskilling (not Apprentice/Trainee)",
                        "RWP",
                        "Retrenched Worker Training Entitlement ' General (not Apprentice/Trainee). Non apprentice/trainee retrenched worker enrolments that commence in 2010"),
                new LabelValue(
                        "RWL - Retrenched Worker 25 years and over, Cert I-IV, upskilling (Apprentice/Trainee)",
                        "RWL",
                        "Retrenched Worker Training Entitlement - Apprentice/Trainee. Retrenched worker apprentice/trainee enrolments that commence in 2010"));
    }

    public static List<LabelValue> getFundingSourceStateIdentifiers_NonGovernmentFunded() {
        return newArrayList(
                new LabelValue(
                        "S - Fee for service - domestic full fee-paying students",
                        "S"),
                new LabelValue(
                        "F - Fee for service - overseas full fee-paying students",
                        FUNDING_SOURCE_STATE_IDENTIFIER_OVERSEAS_FULL_FEE),
                new LabelValue(
                        "SSG - Fee for service - Skills for Growth",
                        "SSG"));
    }

    // FundingSourceNational
    public static List<LabelValue> getFundingSourceNationalIdentifiers() {
        return newArrayList(
                new LabelValue(
                        "Commonwealth and State General Purpose Recurrent",
                        "11",
                        "Funding provided for general and recurrent purposes by the Commonwealth under its agreement with the state or territory, and funds provided by the state for recurrent purposes within that state or territory"),
                new LabelValue(
                        "Commonwealth Specific Purpose Programs",
                        "13",
                        "Funding provided by the Commonwealth for specific purposes to provide training"),
                new LabelValue(
                        "State Specific Purpose Programs",
                        "15",
                        "Funding provided from state or territory governments for specific purposes to provide training"),
                new LabelValue(
                        "Domestic Full Fee-Paying Client",
                        "20",
                        "Payment by a client whose citizenship status is Australian, New Zealand or permanent resident to undertake education and training"),
                new LabelValue(
                        "International Full Fee-Paying Client",
                        FUNDING_SOURCE_NATIONAL_INTERNATIONAL_FULL_FEE,
                        "Payment by a client who holds a student visa, a temporary residency permit, or who resides in an overseas country to undertake education and training (onshore or offshore)"),
                new LabelValue(
                        "Revenue Earned From Another Registered Training Organisation",
                        "80",
                        "Revenue earned from another registered training organisation in terms of subcontracted, auspicing, partnership arrangements or similar"));
    }


    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_DID_NOT_GO_TO_SCHOOL = "02";
    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_COMPLETE_YEAR_12 = "12";
    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_NOT_PROVIDED = "@@";

    public static List<LabelValue> getSchoolLevelCompletedIdentifiers() {
        List<LabelValue> identifiers = newArrayList();
        identifiers.add(new LabelValue("Year 12 or equivalent", SCHOOL_LEVE_COMPLETED_IDENTIFIER_COMPLETE_YEAR_12));
        identifiers.add(new LabelValue("Year 11 or equivalent", "11"));
        identifiers.add(new LabelValue("Year 10 or equivalent", "10"));
        identifiers.add(new LabelValue("Year 9 or equivalent", "09"));
        identifiers.add(new LabelValue("Year 8 or below", "08"));
        identifiers.add(new LabelValue("Did not go to school", SCHOOL_LEVE_COMPLETED_IDENTIFIER_DID_NOT_GO_TO_SCHOOL));
        identifiers.add(new LabelValue("Not Provided", SCHOOL_LEVE_COMPLETED_IDENTIFIER_NOT_PROVIDED));
        return identifiers;
    }

    public static final String PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_MISCELLANEOUSEDUCATION = "990";
    public static final String PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_BACHELOR_DEGREE_OR_HIGHER_DEGREE_LEVEL = "008";

    public static List<LabelValue> getPriorEducationalAchievementIdentifiers() {
        List<LabelValue> identifiers = newArrayList();
        identifiers.add(new LabelValue("Bachelor Degree or Higher Degree level", PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_BACHELOR_DEGREE_OR_HIGHER_DEGREE_LEVEL));
        identifiers.add(new LabelValue("Advanced Diploma or Associate Degree Level", "410"));
        identifiers.add(new LabelValue("Diploma Level", "420"));
        identifiers.add(new LabelValue("Certificate IV", "511"));
        identifiers.add(new LabelValue("Certificate III", "514"));
        identifiers.add(new LabelValue("Certificate II", "521"));
        identifiers.add(new LabelValue("Certificate I", "524"));
        identifiers.add(new LabelValue("Miscellaneous Education", PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_MISCELLANEOUSEDUCATION));
        return identifiers;
    }

    public final static String LABOUR_FORCE_STATUS_IDENTIFIER_FULLTIME_EMPLOYEE = "01";

    public static List<LabelValue> getLabourForceStatusIdentifiers() {
        List<LabelValue> identifiers = newArrayList();
        identifiers.add(new LabelValue("Full-time Employee", LABOUR_FORCE_STATUS_IDENTIFIER_FULLTIME_EMPLOYEE));
        identifiers.add(new LabelValue("Part-time Employee", "02"));
        identifiers.add(new LabelValue("Self Employed - Not Employing Others", "03"));
        identifiers.add(new LabelValue("Employer", "04"));
        identifiers.add(new LabelValue("Employed - Unpaid Worker in a Family Business", "05"));
        identifiers.add(new LabelValue("Unemployed - Seeking Full-time Work", "06"));
        identifiers.add(new LabelValue("Unemployed - Seeking Part-time Work", "07"));
        identifiers.add(new LabelValue("Not Employed - Not Seeking Employment", "08"));
        identifiers.add(new LabelValue("Not Provided", "@@"));
        return identifiers;
    }
    public static String getLabourForceStatus(String identifier) {
        return getLabelByValue(getLabourForceStatusIdentifiers(), identifier);
    }

    public static List<LabelValue> getStudyReasonIdentifiers() {
        List<LabelValue> identifiers = newArrayList();
        identifiers.add(new LabelValue("To get a job", "01"));
        identifiers.add(new LabelValue("To develop my existing business", "02"));
        identifiers.add(new LabelValue("To start my own business", "03"));
        identifiers.add(new LabelValue("To try for a different career", "04"));
        identifiers.add(new LabelValue("To get a better job or promotion", "05"));
        identifiers.add(new LabelValue("It was a requirement of my job", "06"));
        identifiers.add(new LabelValue("I wanted extra skills for my job", "07"));
        identifiers.add(new LabelValue("To get into another course of study", "08"));
        identifiers.add(new LabelValue("For personal interest or self-development", "12"));
        identifiers.add(new LabelValue("Other reasons", "11"));
        identifiers.add(new LabelValue("Not Provided", "@@"));

        return identifiers;
    }

    public static String getConcessionType(String concessionTypeIdentifier) {
        return getLabelByValue(getConcessionTypeIdentifiers(), concessionTypeIdentifier);
    }

    public static String getLabelByValue(List<LabelValue> identifiers, String value) {
        for (LabelValue id : identifiers) {
            if(id.getValue().equals(value)) {
                return id.getLabel();
            }
        }
        return null;
    }

}
