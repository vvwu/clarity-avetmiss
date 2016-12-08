package avetmiss.domain;

import avetmiss.util.LabelValue;

import java.util.List;
import java.util.Optional;

import static avetmiss.util.LabelValue.labelValue;
import static java.util.Arrays.asList;

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
        return asList(
                labelValue("VCE Scholarship", "G"),
                labelValue("Health Care Card", "H"),
                labelValue("Job Seeker and concession card holder (covered by a current, relevant pensioner Concession Card, Health Care Card or Veteran's Gold Card)", "J"),
                labelValue("Job Seeker AND NOT currently holding a relevant Pensioner Concession Card, Health Care Card or Veteran's Gold Card", "K"),
                labelValue("Prisoner", "M"),
                labelValue("Other", "O"),
                labelValue("Pensioner Concession Card", "P"),
                labelValue("Veteran Gold Card Concession", "V"),
                labelValue("None", CONCESSION_TYPE_IDENTIFIER_NONE));
    }

    public static final String INDIGENOUS_STATUS_IDENTIFIERS_NEITHER = "4";

    public static List<LabelValue> getIndigenousStatusIdentifiers() {
        return asList(
                labelValue("No", INDIGENOUS_STATUS_IDENTIFIERS_NEITHER, "No, neither Aboriginal nor Torres Strait Islander"),
                labelValue("Yes, Aboriginal", "1"),
                labelValue("Yes, Torres Strait Islander", "2"),
                labelValue("Yes, Aboriginal AND Torres Strait Islander", "3"),
                labelValue("Not Provided", "@"));
    }

    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_APPLICABLE = " ";
    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_PROVIDED = "@";
    public static final String ENGLISH_PROFICIENCY_IDENTIFIERS_VERY_WELL = "1";

    public static List<LabelValue> getEnglishProficiencyIdentifiers() {
        return asList(
                labelValue("Very well", ENGLISH_PROFICIENCY_IDENTIFIERS_VERY_WELL),
                labelValue("Well", "2"),
                labelValue("Not well", "3"),
                labelValue("Not at all", "4"),
                // labelValue("Not Applicable - English is my main language", ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_APPLICABLE),
                labelValue("Not Provided", ENGLISH_PROFICIENCY_IDENTIFIERS_NOT_PROVIDED));
    }

    public static final String STATE_IDENTIFIERS_VIC = "02";
    public static final String STATE_IDENTIFIERS_OVERSEAS = "99";

    /**
     * Returns a list of Australian State names and their correspondent avatmiss
     * code.
     */
    public static List<LabelValue> getStateIdentifiers() {
        return asList(
                labelValue("VIC", STATE_IDENTIFIERS_VIC),
                labelValue("NSW", "01"),
                labelValue("SA", "04"),
                labelValue("QLD", "03"),
                labelValue("NT", "07"),
                labelValue("WA", "05"),
                labelValue("ACT", "08"),
                labelValue("TAS", "06"),
                // we probably don't need this
                // labelValue("Other Australian Territories or Dependencies", "09");
                labelValue("Overseas", STATE_IDENTIFIERS_OVERSEAS));
    }

    public static List<LabelValue> getLevelOfEducationIdentifier() {
        return asList(
                labelValue("Graduate Diploma", "211"),
                labelValue("Professional Specialist Qualification at Graduate Diploma Level", "213"),
                labelValue("Graduate Certificate", "221"),
                labelValue("Professional Specialist Qualification at Graduate Certificate Level", "222"),
                labelValue("Bachelor Degree (Honours)", "311"),
                labelValue("Bachelor Degree (Pass)", "312"),
                labelValue("Advanced Diploma", "411"),
                labelValue("Associate Degree", "413"),
                labelValue("Diploma", "421"),
                labelValue("Certificate IV", "511"),
                labelValue("Certificate III", "514"),
                labelValue("Certificate II", "521"),
                labelValue("Certificate I", "524"),
                labelValue("Year 12", "611"),
                labelValue("Year 11", "613"),
                labelValue("Year 10", "621"),
                labelValue("Other Non-award Courses", "912"),
                labelValue("Statement of Attainment Not Identifiable by Level", "991"),
                labelValue("Bridging and Enabling Courses Not Identifiable by Level", "992"),
                labelValue("Education not elsewhere classified", "999"));
    }

    public static List<LabelValue> getDisabilityTypeIdentifiers() {
        return asList(
                labelValue("Hearing/Deaf", "11"),
                labelValue("Physical", DISABILITY_TYPE_IDENTIFIER_PHYSICAL),
                labelValue("Intellectual", "13"),
                labelValue("Learning", DISABILITY_TYPE_IDENTIFIER_LEARNING),
                labelValue("Mental Illness", "15"),
                labelValue("Acquired Brain Impairment", "16"),
                labelValue("Vision", "17"),
                labelValue("Medical Condition", "18"),
                labelValue("Other", DISABILITY_TYPE_OTHER),
                labelValue("Not Specified", DISABILITY_TYPE_IDENTIFIER_NOT_SPECIFIED));
    }

    public static List<LabelValue> getLanguageIdentifiers() {
        return asList(
                labelValue("Aboriginal English, so described", "8998"),
                labelValue("Acehnese", "6513"),
                labelValue("Acholi", "9201"),
                labelValue("Adnymathanha", "8901"),
                labelValue("African Languages", "9200"),
                labelValue("Afrikaans", "1403"),
                labelValue("Akan", "9203"),
                labelValue("Alawa", "8121"),
                labelValue("Albanian", "3901"),
                labelValue("Alyawarr", "8603"),
                labelValue("American Languages", "9101"),
                labelValue("Amharic", "9214"),
                labelValue("Anindilyakwa", "8101"),
                labelValue("Anmatyerr", "8604"),
                labelValue("Antikarinya", "8703"),
                labelValue("Arabana", "8902"),
                labelValue("Arabic", "4202"),
                labelValue("Arandic", "8600"),
                labelValue("Armenian", "4901"),
                labelValue("Aromunian (Macedo-Romanian)", "3903"),
                labelValue("Arrernte", "8605"),
                labelValue("Assamese", "5213"),
                labelValue("Assyrian", "4203"),
                labelValue("Auslan", "9701"),
                labelValue("AUSTRALIAN INDIGENOUS LANGUAGES", "8000"),
                labelValue("Azeri", "4302"),
                labelValue("Balinese", "6514"),
                labelValue("Balochi", "4104"),
                labelValue("Baltic", "3100"),
                labelValue("Bandjalang", "8903"),
                labelValue("Banyjima", "8904"),
                labelValue("Bardi", "8801"),
                labelValue("Basque", "2901"),
                labelValue("Batjala", "8905"),
                labelValue("Belorussian", "3401"),
                labelValue("Bemba", "9215"),
                labelValue("Bengali", "5201"),
                labelValue("Bidjara", "8906"),
                labelValue("Bikol", "6515"),
                labelValue("Bilinarra", "8504"),
                labelValue("Bisaya", "6501"),
                labelValue("Bislama", "9402"),
                labelValue("Bosnian", "3501"),
                labelValue("Bulgarian", "3502"),
                labelValue("Bunuba", "8802"),
                labelValue("Burarra", "8102"),
                labelValue("Burmese", "6101"),
                labelValue("Cantonese", "7101"),
                labelValue("Cape York Peninsula Languages", "8300"),
                labelValue("Catalan", "2301"),
                labelValue("Cebuano", "6502"),
                labelValue("Celtic", "1100"),
                labelValue("Chinese", "7100"),
                labelValue("Creole", "0005"),
                labelValue("Croatian", "3503"),
                labelValue("Czech", "3601"),
                labelValue("Daatiwuy", "8233"),
                labelValue("Dalabon", "8122"),
                labelValue("Danish", "1501"),
                labelValue("Dari", "4105"),
                labelValue("Dhalwangu", "8221"),
                labelValue("Dhanggatti", "8907"),
                labelValue("Dhangu", "8210"),
                labelValue("Dhay'yi", "8220"),
                labelValue("Dhivehi", "5214"),
                labelValue("Dhuwal", "8230"),
                labelValue("Dhuwala", "8240"),
                labelValue("Dhuwaya", "8241"),
                labelValue("Dinka", "9216"),
                labelValue("Diyari", "8908"),
                labelValue("Djabugay", "8305"),
                labelValue("Djambarrpuyngu", "8231"),
                labelValue("Djapu", "8232"),
                labelValue("Djarrwark", "8222"),
                labelValue("Djinang", "8250"),
                labelValue("Djinba", "8260"),
                labelValue("Dravidian", "5100"),
                labelValue("Dutch", "1401"),
                labelValue("Dyirbal", "8306"),
                labelValue("East Slavic", "3400"),
                labelValue("EASTERN ASIAN LANGUAGES", "7000"),
                labelValue("EASTERN EUROPEAN LANGUAGES", "3000"),
                labelValue("English", "1201"),
                labelValue("Estonian", "1601"),
                labelValue("Ewe", "9217"),
                labelValue("Fijian", "9301"),
                labelValue("Filipino", "6512"),
                labelValue("Finnish", "1602"),
                labelValue("French", "2101"),
                labelValue("French Creole", "0006"),
                labelValue("Frisian", "1402"),
                labelValue("Ga", "9218"),
                labelValue("Gaelic (Scotland)", "1101"),
                labelValue("Galpu", "8211"),
                labelValue("Gamilaraay", "8911"),
                labelValue("Ganalbingu", "8261"),
                labelValue("Garrwa", "8912"),
                labelValue("Garuwali", "8913"),
                labelValue("Georgian", "4902"),
                labelValue("German", "1301"),
                labelValue("Gilbertese", "9302"),
                labelValue("Girramay", "8307"),
                labelValue("Githabul", "8914"),
                labelValue("Golumala", "8212"),
                labelValue("Gooniyandi", "8803"),
                labelValue("Greek", "2201"),
                labelValue("Gudanji", "8123"),
                labelValue("Gujarati", "5202"),
                labelValue("Gumatj", "8242"),
                labelValue("Gumbaynggir", "8915"),
                labelValue("Gun-nartpa", "8125"),
                labelValue("Gundjeihmi", "8124"),
                labelValue("Gupapuyngu", "8243"),
                labelValue("Gurindji", "8505"),
                labelValue("Gurindji Kriol", "8506"),
                labelValue("Gurr-goni", "8126"),
                labelValue("Guugu Yimidhirr", "8302"),
                labelValue("Guyamirrilili", "8244"),
                labelValue("Haka", "6102"),
                labelValue("Hakka", "7102"),
                labelValue("Harari", "9221"),
                labelValue("Hausa", "9222"),
                labelValue("Hawaiian English", "9403"),
                labelValue("Hebrew", "4204"),
                labelValue("Hindi", "5203"),
                labelValue("Hmong", "6201"),
                labelValue("Hmong-Mien", "6200"),
                labelValue("Hokkien", "7103"),
                labelValue("Hungarian", "3301"),
                labelValue("Iban", "6516"),
                labelValue("Iberian Romance", "2300"),
                labelValue("Icelandic", "1502"),
                labelValue("Igbo", "9223"),
                labelValue("IIokano", "6503"),
                labelValue("Indo-Aryan", "5200"),
                labelValue("Indonesian", "6504"),
                labelValue("Invented Languages", "9601"),
                labelValue("Iranic", "4100"),
                labelValue("Irish", "1102"),
                labelValue("Italian", "2401"),
                labelValue("Iwaidja", "8127"),
                labelValue("Jaminjung", "8128"),
                labelValue("Japanese", "7201"),
                labelValue("Jaru", "8507"),
                labelValue("Javanese", "6518"),
                labelValue("Jawoyn", "8131"),
                labelValue("Jingulu", "8132"),
                labelValue("Kanai", "8916"),
                labelValue("Kannada", "5101"),
                labelValue("Karajarri", "8917"),
                labelValue("Karen", "6103"),
                labelValue("Kariyarra", "8918"),
                labelValue("Kartujarra", "8704"),
                labelValue("Kashmiri", "5215"),
                labelValue("Kaurna", "8921"),
                labelValue("Kayardild", "8922"),
                labelValue("Kaytetye", "8606"),
                labelValue("Khmer", "6301"),
                labelValue("Kija", "8923"),
                labelValue("Kikuyu", "9224"),
                labelValue("Kimberley Area Languages", "8800"),
                labelValue("Kiwai", "9502"),
                labelValue("Koko-Bera", "8308"),
                labelValue("Konkani", "5204"),
                labelValue("Korean", "7301"),
                labelValue("Krio", "9225"),
                labelValue("Kriol", "8924"),
                labelValue("Kukatha", "8705"),
                labelValue("Kukatja", "8706"),
                labelValue("Kuku Yalanji", "8301"),
                labelValue("Kunbarlang", "8133"),
                labelValue("Kune", "8134"),
                labelValue("Kuninjku", "8135"),
                labelValue("Kunwinjku", "8108"),
                labelValue("Kurdish", "4101"),
                labelValue("Kuuk Thayorre", "8311"),
                labelValue("Kuuku-Ya'u", "8303"),
                labelValue("Lamalama", "8312"),
                labelValue("Lao", "6401"),
                labelValue("Lardil", "8925"),
                labelValue("Larrakiya", "8136"),
                labelValue("Latin", "2902"),
                labelValue("Latvian", "3101"),
                labelValue("Letzeburgish", "1302"),
                labelValue("Light Warlpiri", "8508"),
                labelValue("Lithuanian", "3102"),
                labelValue("Liyagalawumirr", "8235"),
                labelValue("Luganda", "9226"),
                labelValue("Luo", "9227"),
                labelValue("Luritja", "8707"),
                labelValue("Macedonian", "3504"),
                labelValue("Madarrpa", "8245"),
                labelValue("Makaton", "9702"),
                labelValue("Malak Malak", "8137"),
                labelValue("Malay", "6505"),
                labelValue("Malayalam", "5102"),
                labelValue("Malngin", "8511"),
                labelValue("Maltese", "2501"),
                labelValue("Mandarin", "7104"),
                labelValue("Mangala", "8926"),
                labelValue("Mangarrayi", "8138"),
                labelValue("Manggalili", "8246"),
                labelValue("Manyjilyjarra", "8708"),
                labelValue("Maori (Cook Island)", "9303"),
                labelValue("Maori (New Zealand)", "9304"),
                labelValue("Marathi", "5205"),
                labelValue("Maringarr", "8141"),
                labelValue("Marra", "8142"),
                labelValue("Marrangu", "8234"),
                labelValue("Marrithiyel", "8143"),
                labelValue("Martu Wangka", "8711"),
                labelValue("Matngala", "8144"),
                labelValue("Maung", "8111"),
                labelValue("Mauritian Creole", "9205"),
                labelValue("Mayali", "8145"),
                labelValue("Meriam Mir", "8402"),
                labelValue("Middle Eastern Semitic Languages", "4200"),
                labelValue("Miriwoong", "8804"),
                labelValue("Mon", "6303"),
                labelValue("Mon-Khmer", "6300"),
                labelValue("Mongolian", "7902"),
                labelValue("Motu", "9305"),
                labelValue("Mudburra", "8512"),
                labelValue("Murrinh Patha", "8146"),
                labelValue("Muruwari", "8927"),
                labelValue("Na-kara", "8147"),
                labelValue("Narungga", "8928"),
                labelValue("Nauruan", "9306"),
                labelValue("Ndebele", "9228"),
                labelValue("Nepali", "5206"),
                labelValue("Ngaanyatjarra", "8712"),
                labelValue("Ngalakgan", "8151"),
                labelValue("Ngaliwurru", "8152"),
                labelValue("Ngan'gikurunggurr", "8113"),
                labelValue("Ngandi", "8513"),
                labelValue("Ngardi", "8514"),
                labelValue("Ngarinyin", "8805"),
                labelValue("Ngarinyman", "8515"),
                labelValue("Ngarluma", "8931"),
                labelValue("Ngarrindjeri", "8932"),
                labelValue("Nhangu", "8280"),
                labelValue("Nhangu", "8281"),
                labelValue("Niue", "9307"),
                labelValue("Non Verbal", "0001"),
                labelValue("Northern Desert Fringe Area Languages", "8500"),
                labelValue("NORTHERN EUROPEAN LANGUAGES", "1000"),
                labelValue("Norwegian", "1503"),
                labelValue("Not Stated", "@@@@"),
                labelValue("Nuer", "9231"),
                labelValue("Nungali", "8153"),
                labelValue("Nunggubuyu", "8114"),
                labelValue("Nyamal", "8933"),
                labelValue("Nyangumarta", "8934"),
                labelValue("Nyanja (Chichewa)", "9232"),
                labelValue("Nyikina", "8806"),
                labelValue("Nyungar", "8935"),
                labelValue("Oceanian Pidgins and Creoles", "9400"),
                labelValue("Oriya", "5216"),
                labelValue("Oromo", "9206"),
                labelValue("Other Australian Indigenous Languages", "8900"),
                labelValue("Other Eastern Asian Languages", "7900"),
                labelValue("Other Eastern European Languages", "3900"),
                labelValue("OTHER LANGUAGES", "9000"),
                labelValue("Other Southeast Asian Languages", "6999"),
                labelValue("Other Southern Asian Languages", "5999"),
                labelValue("Other Southern European Languages", "2900"),
                labelValue("Other Southwest and Central Asian Languages", "4900"),
                labelValue("Other Yolngu Matha", "8290"),
                labelValue("Paakantyi", "8936"),
                labelValue("Pacific Austronesian Languages", "9300"),
                labelValue("Palyku/Nyiyaparli", "8937"),
                labelValue("Pampangan", "6521"),
                labelValue("Papua New Guinea Papuan Languages", "9500"),
                labelValue("Pashto", "4102"),
                labelValue("Persian (excluding Dari)", "4106"),
                labelValue("Pidgin", "0009"),
                labelValue("Pintupi", "8713"),
                labelValue("Pitcairnese", "9404"),
                labelValue("Pitjantjatjara", "8714"),
                labelValue("Polish", "3602"),
                labelValue("Portuguese", "2302"),
                labelValue("Portuguese Creole", "0008"),
                labelValue("Punjabi", "5207"),
                labelValue("Rembarrnga", "8115"),
                labelValue("Ritharrngu", "8271"),
                labelValue("Romanian", "3904"),
                labelValue("Romany", "3905"),
                labelValue("Rotuman", "9312"),
                labelValue("Russian", "3402"),
                labelValue("Samoan", "9308"),
                labelValue("Scandinavian", "1500"),
                labelValue("Serbian", "3505"),
                labelValue("Seychelles Creole", "9238"),
                labelValue("Shilluk", "9233"),
                labelValue("Shona", "9207"),
                labelValue("Sign Languages", "9700"),
                labelValue("Sindhi", "5208"),
                labelValue("Sinhalese", "5211"),
                labelValue("Slovak", "3603"),
                labelValue("Slovene", "3506"),
                labelValue("Solomon Islands Pijin", "9405"),
                labelValue("Somali", "9208"),
                labelValue("South Slavic", "3500"),
                labelValue("Southeast Asian Austronesian Languages", "6500"),
                labelValue("SOUTHEAST ASIAN LANGUAGES", "6000"),
                labelValue("SOUTHERN ASIAN LANGUAGES", "5000"),
                labelValue("SOUTHERN EUROPEAN LANGUAGES", "2000"),
                labelValue("SOUTHWEST AND CENTRAL ASIAN LANGUAGES", "4000"),
                labelValue("Spanish", "2303"),
                labelValue("Spanish Creole", "0007"),
                labelValue("Swahili", "9211"),
                labelValue("Swedish", "1504"),
                labelValue("Swiss, so described", "0003"),
                labelValue("Tagalog", "6511"),
                labelValue("Tai", "6400"),
                labelValue("Tamil", "5103"),
                labelValue("Tatar", "4303"),
                labelValue("Telugu", "5104"),
                labelValue("Teochew", "7105"),
                labelValue("Tetum", "6507"),
                labelValue("Thai", "6402"),
                labelValue("Tibetan", "7901"),
                labelValue("Tigrinya", "9235"),
                labelValue("Tigr'", "9234"),
                labelValue("Timorese", "6508"),
                labelValue("Tiwi", "8117"),
                labelValue("Tok Pisin", "9401"),
                labelValue("Tokelauan", "9313"),
                labelValue("Tongan", "9311"),
                labelValue("Torres Strait Creole", "8403"),
                labelValue("Torres Strait Island Languages", "8400"),
                labelValue("Tswana", "9236"),
                labelValue("Tulu", "5105"),
                labelValue("Turkic", "4300"),
                labelValue("Turkish", "4301"),
                labelValue("Turkmen", "4304"),
                labelValue("Tuvaluan", "9314"),
                labelValue("Ukrainian", "3403"),
                labelValue("Unknown", "0000"), // 0000 Inadequately described
                labelValue("Urdu", "5212"),
                labelValue("Uygur", "4305"),
                labelValue("Uzbek", "4306"),
                labelValue("Vietnamese", "6302"),
                labelValue("Wajarri", "8938"),
                labelValue("Walmajarri", "8516"),
                labelValue("Wambaya", "8154"),
                labelValue("Wangkajunga", "8715"),
                labelValue("Wangkatha", "8716"),
                labelValue("Wangurri", "8213"),
                labelValue("Wanyjirra", "8517"),
                labelValue("Wardaman", "8155"),
                labelValue("Warlmanpa", "8518"),
                labelValue("Warlpiri", "8521"),
                labelValue("Warnman", "8717"),
                labelValue("Warumungu", "8522"),
                labelValue("Welsh", "1103"),
                labelValue("West Slavic", "3600"),
                labelValue("Western Desert Language", "8700"),
                labelValue("Wik Mungkan", "8304"),
                labelValue("Wik Ngathan", "8314"),
                labelValue("Wiradjuri", "8941"),
                labelValue("Worla", "8807"),
                labelValue("Worrorra", "8808"),
                labelValue("Wu", "7106"),
                labelValue("Wubulkarra", "8247"),
                labelValue("Wunambal", "8811"),
                labelValue("Wurlaki", "8251"),
                labelValue("Xhosa", "9237"),
                labelValue("Yakuy", "8270"),
                labelValue("Yankunytjatjara", "8718"),
                labelValue("Yanyuwa", "8942"),
                labelValue("Yapese", "9315"),
                labelValue("Yawuru", "8812"),
                labelValue("Yiddish", "1303"),
                labelValue("Yidiny", "8313"),
                labelValue("Yindjibarndi", "8943"),
                labelValue("Yinhawangka", "8944"),
                labelValue("Yolngu Matha", "8200"),
                labelValue("Yorta Yorta", "8945"),
                labelValue("Yoruba", "9212"),
                labelValue("Yulparija", "8721"),
                labelValue("Zulu", "9213"));
    }

    public static final String ANZSIC_CODES_NOT_APPLICABLE = "";

    public static List<LabelValue> getAnzsicCodes() {
        return asList(
                labelValue("01 Agriculture", "01"),
                labelValue("02 Aquaculture", "02"),
                labelValue("03 Forestry and Logging", "03"),
                labelValue("04 Fishing, Hunting and Trapping", "04"),
                labelValue("05 Agriculture, Forestry and Fishing Support Services", "05"),
                labelValue("06 Coal Mining", "06"),
                labelValue("07 Oil and Gas Extraction", "07"),
                labelValue("08 Metal Ore Mining", "08"),
                labelValue("09 Non-Metallic Mineral Mining and Quarrying", "09"),
                labelValue("10 Exploration and Other Mining Support Services", "10"),
                labelValue("11 Food Product Manufacturing", "11"),
                labelValue("12 Beverage and Tobacco Product Manufacturing", "12"),
                labelValue("13 Textile, Leather, Clothing and Footwear Manufacturing", "13"),
                labelValue("14 Wood Product Manufacturing", "14"),
                labelValue("15 Pulp, Paper and Converted Paper Product Manufacturing", "15"),
                labelValue("16 Printing (including the Reproduction of Recorded Media)", "16"),
                labelValue("17 Petroleum and Coal Product Manufacturing", "17"),
                labelValue("18 Basic Chemical and Chemical Product Manufacturing", "18"),
                labelValue("19 Polymer Product and Rubber Product Manufacturing", "19"),
                labelValue("20 Non-Metallic Mineral Product Manufacturing", "20"),
                labelValue("21 Primary Metal and Metal Product Manufacturing", "21"),
                labelValue("22 Fabricated Metal Product Manufacturing", "22"),
                labelValue("23 Transport Equipment Manufacturing", "23"),
                labelValue("24 Machinery and Equipment Manufacturing", "24"),
                labelValue("25 Furniture and Other Manufacturing", "25"),
                labelValue("26 Electricity Supply", "26"),
                labelValue("27 Gas Supply", "27"),
                labelValue("28 Water Supply, Sewerage and Drainage Services", "28"),
                labelValue("29 Waste Collection, Treatment and Disposal Services", "29"),
                labelValue("30 Building Construction", "30"),
                labelValue("31 Heavy and Civil Engineering Construction", "31"),
                labelValue("32 Construction Services", "32"),
                labelValue("33 Basic Material Wholesaling", "33"),
                labelValue("34 Machinery and Equipment Wholesaling", "34"),
                labelValue("35 Motor Vehicle and Motor Vehicle Parts Wholesaling", "35"),
                labelValue("36 Grocery, Liquor and Tobacco Product Wholesaling", "36"),
                labelValue("37 Other Goods Wholesaling", "37"),
                labelValue("38 Commission-Based Wholesaling", "38"),
                labelValue("39 Motor Vehicle and Motor Vehicle Parts Retailing", "39"),
                labelValue("40 Fuel Retailing", "40"),
                labelValue("41 Food Retailing", "41"),
                labelValue("42 Other Store-Based Retailing", "42"),
                labelValue("43 Non-Store Retailing and Retail Commission-Based Buying and/or Selling", "43"),
                labelValue("44 Accommodation", "44"),
                labelValue("45 Food and Beverage Services", "45"),
                labelValue("46 Road Transport", "46"),
                labelValue("47 Rail Transport", "47"),
                labelValue("48 Water Transport", "48"),
                labelValue("49 Air and Space Transport", "49"),
                labelValue("50 Other Transport", "50"),
                labelValue("51 Postal and Courier Pick-up and Delivery Services", "51"),
                labelValue("52 Transport Support Services", "52"),
                labelValue("53 Warehousing and Storage Services", "53"),
                labelValue("54 Publishing (except Internet and Music Publishing)", "54"),
                labelValue("55 Motion Picture and Sound Recording Activities", "55"),
                labelValue("56 Broadcasting (except Internet)", "56"),
                labelValue("57 Internet Publishing and Broadcasting", "57"),
                labelValue("58 Telecommunications Services", "58"),
                labelValue("59 Internet Service Providers, Web Search Portals and Data Processing Services", "59"),
                labelValue("60 Library and Other Information Services", "60"),
                labelValue("62 Finance", "62"),
                labelValue("63 Insurance and Superannuation Funds", "63"),
                labelValue("64 Auxiliary Finance and Insurance Services", "64"),
                labelValue("66 Rental and Hiring Services (except Real Estate)", "66"),
                labelValue("67 Property Operators and Real Estate Services", "67"),
                labelValue("69 Professional, Scientific and Technical Services (Except Computer System Design and Related Services)", "69"),
                labelValue("70 Computer System Design and Related Services", "70"),
                labelValue("72 Administrative Services", "72"),
                labelValue("73 Building Cleaning, Pest Control and Other Support Services", "73"),
                labelValue("75 Public Administration", "75"),
                labelValue("76 Defence", "76"),
                labelValue("77 Public Order, Safety and Regulatory Services", "77"),
                labelValue("80 Preschool and School Education", "80"),
                labelValue("81 Tertiary Education", "81"),
                labelValue("82 Adult, Community and Other Education", "82"),
                labelValue("84 Hospitals", "84"),
                labelValue("85 Medical and Other Health Care Services", "85"),
                labelValue("86 Residential Care Services", "86"),
                labelValue("87 Social Assistance Services", "87"),
                labelValue("89 Heritage Activities", "89"),
                labelValue("90 Creative and Performing Arts Activities", "90"),
                labelValue("91 Sports and Recreation Activities", "91"),
                labelValue("92 Gambling Activities", "92"),
                labelValue("94 Repair and Maintenance", "94"),
                labelValue("95 Personal and Other Services", "95"),
                labelValue("96 Private Households Employing Staff and Undifferentiated Goods- and Service-Producing Activities of Households for Own Use", "96"));
    }

    public static final String FUNDING_SOURCE_NATIONAL_IDENTIFIER_INTERNATIONAL_FULL_FEE_PAYING = "30";
    public static final String FUNDING_SOURCE_STATE_IDENTIFIER_OVERSEAS_FEE_FOR_SERVICE = "F";


    public static List<LabelValue> getFundingSourceStateIdentifiers_GovernmentFunded() {
        return asList(
                labelValue(
                        "P - Diploma and above (not Apprentice/Trainee)",
                        "P",
                        "General training delivery (non-Apprentice/Trainee training)"),
                labelValue(
                        "L - Diploma and above (Apprentice/Trainee)",
                        "L",
                        "Apprentice/Trainee. Training activity funded by Skills Victoria where the student is a registered apprentice/trainee."),
                labelValue(
                        "PSG - Skills for Growth - (not Apprentice/Trainee)",
                        "PSG",
                        "Skills for Growth - General (not Apprentice/Trainee).Training delivery to non-Apprentice/Trainees through Skills for Growth funding"),
                labelValue(
                        "LSG - Skills for Growth - (Apprentice/Trainee)",
                        "LSG",
                        "Skills for Growth - Apprentice/Trainee. Training delivery to apprentices and trainees through Skills for Growth funding"),
                labelValue(
                        "YRP - 19 to 24 Foundation or Cert I-IV and upskilling (not Apprentice/Trainee)",
                        "YRP",
                        "Youth Compact ' General (not Apprentice/Trainee)  (non-TAFE RTO only). Training delivery to eligible young people aged 15-24 as at 1 January of year of course commencement"),
                labelValue(
                        "Youth Compact - Apprentice/Trainee (non-TAFE RTOs only)",
                        "YRL",
                        "Youth Compact - Apprentice/Trainee (non-TAFE RTO only). Training delivery to eligible young people aged 15-24 as at 1 January of year of program (course) commencement. Program (course) enrolment commenced in 2010 and is continuing in 2015"),
                labelValue(
                        "RWP - Retrenched Worker 25 years and over, Cert I-IV, upskilling (not Apprentice/Trainee)",
                        "RWP",
                        "Retrenched Worker Training Entitlement ' General (not Apprentice/Trainee). Non apprentice/trainee retrenched worker enrolments that commence in 2010"),
                labelValue(
                        "RWL - Retrenched Worker 25 years and over, Cert I-IV, upskilling (Apprentice/Trainee)",
                        "RWL",
                        "Retrenched Worker Training Entitlement - Apprentice/Trainee. Retrenched worker apprentice/trainee enrolments that commence in 2010"));
    }

    public static List<LabelValue> getFundingSourceStateIdentifiers_NonGovernmentFunded() {
        return asList(
                labelValue(
                        "S - Fee for service - domestic full fee-paying students",
                        "S"),
                labelValue(
                        "F - Fee for service - overseas full fee-paying students",
                        FUNDING_SOURCE_STATE_IDENTIFIER_OVERSEAS_FULL_FEE),
                labelValue(
                        "SSG - Fee for service - Skills for Growth",
                        "SSG"));
    }

    public static Optional<LabelValue> getFundingSourceState(String fundingSourceStateIdentifier) {
        Optional<LabelValue> label = getLabelValueByValue(getFundingSourceStateIdentifiers_GovernmentFunded(), fundingSourceStateIdentifier);
        if(label.isPresent()) {
            return label;
        }

        return getLabelValueByValue(getFundingSourceStateIdentifiers_NonGovernmentFunded(), fundingSourceStateIdentifier);
    }


    // FundingSourceNational
    public static List<LabelValue> getFundingSourceNationalIdentifiers() {
        return asList(
                labelValue(
                        "Commonwealth and State General Purpose Recurrent",
                        "11",
                        "Funding provided for general and recurrent purposes by the Commonwealth under its agreement with the state or territory, and funds provided by the state for recurrent purposes within that state or territory"),
                labelValue(
                        "Commonwealth Specific Purpose Programs",
                        "13",
                        "Funding provided by the Commonwealth for specific purposes to provide training"),
                labelValue(
                        "State Specific Purpose Programs",
                        "15",
                        "Funding provided from state or territory governments for specific purposes to provide training"),
                labelValue(
                        "Domestic Full Fee-Paying Client",
                        "20",
                        "Payment by a client whose citizenship status is Australian, New Zealand or permanent resident to undertake education and training"),
                labelValue(
                        "International Full Fee-Paying Client",
                        FUNDING_SOURCE_NATIONAL_INTERNATIONAL_FULL_FEE,
                        "Payment by a client who holds a student visa, a temporary residency permit, or who resides in an overseas country to undertake education and training (onshore or offshore)"),
                labelValue(
                        "Revenue Earned From Another Registered Training Organisation",
                        "80",
                        "Revenue earned from another registered training organisation in terms of subcontracted, auspicing, partnership arrangements or similar"));
    }


    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_DID_NOT_GO_TO_SCHOOL = "02";
    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_COMPLETE_YEAR_12 = "12";
    public static final String SCHOOL_LEVE_COMPLETED_IDENTIFIER_NOT_PROVIDED = "@@";

    public static List<LabelValue> getSchoolLevelCompletedIdentifiers() {
        return asList(
                labelValue("Year 12 or equivalent", SCHOOL_LEVE_COMPLETED_IDENTIFIER_COMPLETE_YEAR_12),
                labelValue("Year 11 or equivalent", "11"),
                labelValue("Year 10 or equivalent", "10"),
                labelValue("Year 9 or equivalent", "09"),
                labelValue("Year 8 or below", "08"),
                labelValue("Did not go to school", SCHOOL_LEVE_COMPLETED_IDENTIFIER_DID_NOT_GO_TO_SCHOOL),
                labelValue("Not Provided", SCHOOL_LEVE_COMPLETED_IDENTIFIER_NOT_PROVIDED));
    }

    public static final String PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_MISCELLANEOUSEDUCATION = "990";
    public static final String PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_BACHELOR_DEGREE_OR_HIGHER_DEGREE_LEVEL = "008";

    public static List<LabelValue> getPriorEducationalAchievementIdentifiers() {
        return asList(
                labelValue("Bachelor Degree or Higher Degree level", PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_BACHELOR_DEGREE_OR_HIGHER_DEGREE_LEVEL),
                labelValue("Advanced Diploma or Associate Degree Level", "410"),
                labelValue("Diploma Level", "420"),
                labelValue("Certificate IV", "511"),
                labelValue("Certificate III", "514"),
                labelValue("Certificate II", "521"),
                labelValue("Certificate I", "524"),
                labelValue("Miscellaneous Education", PRIOR_EDUCATIONAL_ACHIEVEMENT_IDENTIFIER_MISCELLANEOUSEDUCATION));
    }

    public final static String LABOUR_FORCE_STATUS_IDENTIFIER_FULLTIME_EMPLOYEE = "01";

    public static List<LabelValue> getLabourForceStatusIdentifiers() {
        return asList(
                labelValue("Full-time Employee", LABOUR_FORCE_STATUS_IDENTIFIER_FULLTIME_EMPLOYEE),
                labelValue("Part-time Employee", "02"),
                labelValue("Self Employed - Not Employing Others", "03"),
                labelValue("Employer", "04"),
                labelValue("Employed - Unpaid Worker in a Family Business", "05"),
                labelValue("Unemployed - Seeking Full-time Work", "06"),
                labelValue("Unemployed - Seeking Part-time Work", "07"),
                labelValue("Not Employed - Not Seeking Employment", "08"),
                labelValue("Not Provided", "@@"));
    }

    public static List<LabelValue> getStudyReasonIdentifiers() {
        return asList(
                labelValue("To get a job", "01"),
                labelValue("To develop my existing business", "02"),
                labelValue("To start my own business", "03"),
                labelValue("To try for a different career", "04"),
                labelValue("To get a better job or promotion", "05"),
                labelValue("It was a requirement of my job", "06"),
                labelValue("I wanted extra skills for my job", "07"),
                labelValue("To get into another course of study", "08"),
                labelValue("For personal interest or self-development", "12"),
                labelValue("Other reasons", "11"),
                labelValue("Not Provided", "@@"));
    }

    public static Optional<LabelValue> getStudyReason(String identifier) {
        return getLabelValueByValue(getStudyReasonIdentifiers(), identifier);
    }

    public static Optional<LabelValue> getLabelValueByValue(List<LabelValue> identifiers, String value) {
        return identifiers.stream()
                .filter(s -> s.getValue().equals(value))
                .findFirst();
    }

}
