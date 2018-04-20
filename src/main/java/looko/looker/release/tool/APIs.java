package looko.looker.release.tool;

public class APIs {

    public static final String KEY = "177A5CAFEDAE3B23DA10115A4C95C9B9";

//    public static final String MySteamId = "76561198367830998";

    //ISteamApps
    public static final String GetAppList = "http://api.steampowered.com/ISteamApps/GetAppList/v2";

    //ISteamNews
//    public static final String GetNewsForApp = "http://api.steampowered.com/ISteamNews/GetNewsForApp/v2?key="+KEY;

    //ISteamUser
    public static final String GetFriendList = "http://api.steampowered.com/ISteamUser/GetFriendList/v1?key="+KEY;
    public static final String GetPlayerSummaries = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2?key="+KEY;
    public static final String ResolveVanityURL = "http://api.steampowered.com/ISteamUser/ResolveVanityURL/v1?key="+KEY;

    //ISteamUserStats
    public static final String GetGlobalAchiPercForApp = "http://api.steampowered.com/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v2?key="+KEY;
//    public static final String GetGlobalStatsForGame = "http://api.steampowered.com/ISteamUserStats/GetGlobalStatsForGame/v1?key="+KEY;
    public static final String GetNumOfCurrentPlayers = "http://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1?key="+KEY;
    public static final String GetPlayerAchi = "http://api.steampowered.com/ISteamUserStats/GetPlayerAchievements/v1?key="+KEY;
    public static final String GetSchemaForGame = "http://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v2?key="+KEY;
    //public static final String GetUserStatsForGame = "http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v2?key="+KEY; out

    //IPlayerService
    public static final String GetRecentlyPlayedGames = "http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1?key="+KEY;
    public static final String GetOwnedGames = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v1?key="+KEY;
    public static final String GetSteamLevel = "http://api.steampowered.com/IPlayerService/GetSteamLevel/v1?key="+KEY;
//    public static final String GetBadges = "http://api.steampowered.com/IPlayerService/GetBadges/v1?key="+KEY;

    //ISteamWebAPIUtil
    public static final String GetServerInfo = "http://api.steampowered.com/ISteamWebAPIUtil/GetServerInfo/v1?key="+KEY;
    public static final String GetSupportedAPIList = "http://api.steampowered.com/ISteamWebAPIUtil/GetSupportedAPIList/v1?key="+KEY;
}
