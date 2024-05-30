package com.example.fakespotify;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Locale;
import java.util.Random;

public class Main extends Application {

    public static int adminUID = 22 ;
    public static InputDevice inputDevice = new InputDevice() ;
    public static OutputDevice outputDevice = new OutputDevice() ;
    public static App app = App.CreateApp(inputDevice,outputDevice) ;
    public static String username = "" ;
    public static String password = "" ;
    public static SimpleIntegerProperty UID = new SimpleIntegerProperty(0) ;
    static LoginScreen loginScreen = new LoginScreen("Login") ;
    static SignUpScreen signUpScreen = new SignUpScreen("Sign Up") ;
    static MainScreen mainScreen = new MainScreen("Main Screen") ;
    static ProfileScreen profileScreen = new ProfileScreen("Profile") ;
    static PlayListsScreen playListsScreen = new PlayListsScreen("Playlists") ;
    static CreatePlaylistScreen createPlaylistScreen = new CreatePlaylistScreen("Create a new Playlist") ;
    static EditPlaylistScreen editPlaylistScreen = new EditPlaylistScreen("Edit Playlist") ;

    static ManageUsersScreen manageUsersScreen = new ManageUsersScreen("Manage Users") ;
    static Runnable switchToSignUpScreen ;
    static Runnable switchToLoginScreen ;
    static Runnable switchToProfileScreen ;
    static Runnable switchToMainScreen ;
    static Runnable switchToPlaylistsScreen ;
    static Runnable switchToCreatePlaylistScreen ;
    static Runnable switchToEditPlaylistScreen ;
    static Runnable switchToManageUsersScreen ;

    @Override
    public void start(Stage stage) throws IOException {

        switchToSignUpScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(signUpScreen.getScene());
                stage.setTitle(signUpScreen.title);
            }
        };
        switchToManageUsersScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(manageUsersScreen.getScene());
                stage.setTitle(manageUsersScreen.title);
            }
        };
        switchToLoginScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(loginScreen.getScene());
                stage.setTitle(loginScreen.title);
            }
        };
        switchToProfileScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(profileScreen.getScene());
                stage.setTitle(profileScreen.title);
            }
        } ;
        switchToMainScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(mainScreen.getScene());
                stage.setTitle(mainScreen.title);
            }
        };
        switchToPlaylistsScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(playListsScreen.getScene());
                stage.setTitle(playListsScreen.title);
            }
        };
        switchToCreatePlaylistScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(createPlaylistScreen.getScene());
                stage.setTitle(createPlaylistScreen.title);
            }
        };
        switchToEditPlaylistScreen = new Runnable() {
            @Override
            public void run() {
                stage.setScene(editPlaylistScreen.getScene());
                stage.setTitle(editPlaylistScreen.title);
            }
        };

        UID.addListener((observable,oldVal,newVal) -> {
            playListsScreen.UpdateCurrentPlaylists();
            if(newVal.equals(adminUID) && !oldVal.equals(adminUID))
                profileScreen.AddManageUsersButton();
            if(!newVal.equals(adminUID) && oldVal.equals(adminUID))
                profileScreen.RemoveManageUsersButton();
        });

        stage.setTitle(loginScreen.title);
        stage.setScene(loginScreen.getScene());
        stage.show();

        app.BeforeExecute();
        app.Execute();

        AddSongsFromSongsFolderToDatabase();
    }

    public static int CreateAccount(String accountName, String accountPassword) // protected
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            String query = "INSERT INTO users (name,password) VALUES (?,?) ;";
            PreparedStatement s1 = connection.prepareStatement(query) ;
            s1.setString(1,accountName);
            s1.setString(2,accountPassword);
            s1.executeUpdate() ;

            query = "SELECT LAST_INSERT_ID();";
            Statement s2 = connection.createStatement() ;
            ResultSet resultSet = s2.executeQuery(query) ;
            if(resultSet.next())
                return resultSet.getInt(1) ;
            return -1 ;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return -1 ;
    }

    public static void DeletePlaylist(int id)//ok
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            String query = "DROP TABLE playlist_" + id + ";" ;
            Statement s1 = connection.createStatement() ;
            s1.executeUpdate(query) ;

            query = "DELETE FROM playlists WHERE playlist_id = ? ;" ;
            PreparedStatement s2 = connection.prepareStatement(query) ;
            s2.setInt(1,id);
            s2.executeUpdate() ;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static int CheckIfAccountExists(String accountName, String accountPassword)//tank
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            String query = "SELECT password,id FROM users WHERE name = ? ;"  ;
            //accountName
            PreparedStatement s1 = connection.prepareStatement(query) ;
            s1.setString(1,accountName);
            ResultSet resultSet = s1.executeQuery() ;
            while (resultSet.next())
            {
                if(resultSet.getString(1).equals(accountPassword))
                    return resultSet.getInt(2) ;
            }
            return -1 ;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return -1 ;
    }

    public static ArrayList<PlayList> GetPlaylistsFromDatabaseForUser(int uid)//tank
    {
        ArrayList<PlayList> results = new ArrayList<>() ;

        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            String query = "SELECT playlist_id,name FROM playlists WHERE user_id = ?;" ;
            PreparedStatement s1 = connection.prepareStatement(query) ;
            s1.setInt(1,uid);
            ResultSet resultSet = s1.executeQuery() ;

            Statement songStatement = connection.createStatement() ;
            String playlistName = "" ;
            int playlistId = -1 ;
            while (resultSet.next()) {
                ArrayList<Song> songs = new ArrayList<>() ;
                playlistName = resultSet.getString(2) ;
                playlistId = resultSet.getInt(1) ;
                query = "SELECT song_id FROM playlist_" + playlistId + ";" ;
                ResultSet songResultSet = songStatement.executeQuery(query);
                while(songResultSet.next())
                    songs.add(GetSongWithId(songResultSet.getInt(1))) ;
                PlayList newPlaylist = new PlayList(playlistName,playlistId,songs) ;
                results.add(newPlaylist);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return results ;
    }

    public static void CreatePlayListForUser(int uid, ArrayList<Song> songs, String playlistName)//tank
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            String query = "INSERT INTO playlists (name,user_id) VALUES (?,?);" ;
            PreparedStatement s1 = connection.prepareStatement(query) ;
            s1.setString(1,playlistName);
            s1.setInt(2,uid);
            s1.executeUpdate() ;

            query = "SELECT LAST_INSERT_ID();" ;
            Statement s2 = connection.createStatement() ;
            ResultSet resultSet = s2.executeQuery(query) ;
            int playlist_id = -1 ;
            if(resultSet.next())
                playlist_id = resultSet.getInt(1) ;

            Statement s3 = connection.createStatement() ;
            query = "CREATE TABLE playlist_" + playlist_id + "(song_id INT)" ;
            s3.executeUpdate(query) ;

            for(int i = 0 ; i < songs.size() ; i++)
            {
                query = "INSERT INTO playlist_" + playlist_id + " (song_id) VALUES (" + songs.get(i).id + ");" ;
                s3.executeUpdate(query) ;
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void SetAccount(String accountName,String accountPassword, int uid)
    {
        username = accountName ;
        password = accountPassword ;
        UID.set(uid);
        profileScreen.UpdateUsername();
    }

    public static ArrayList<Song> GetSongsFromDatabase()
    {
        ArrayList<Song> songs = new ArrayList<>() ;
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            Statement statement = connection.createStatement() ;
            String query = "SELECT * FROM songs" ;
            ResultSet resultSet = statement.executeQuery(query) ;

            while (resultSet.next())
            {
                String path = System.getProperty("user.id") + "/songs/" + resultSet.getString(6) ;
                int id = resultSet.getInt(1) ;
                String name = resultSet.getString(2) ;
                String artist = resultSet.getString(3) ;
                String album = resultSet.getString(4) ;
                String genre = resultSet.getString(5) ;
                ArrayList<String> genres = new ArrayList<>() ;
                genres.add(genre) ;

                songs.add(new Song(path,name,artist,album,genres,0,id,app.musicPlayer)) ;
            }
            return songs;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String GetRandomName()
    {
        Random rand = new Random() ;
        String name = "" ;
        for(int i = 0 ; i < 5 ; i++)
            name += (char)(rand.nextInt(26) + 'a') ;
        return name ;
    }
    public static ArrayList<String> arrayOf(String s)
    {
        ArrayList<String> result = new ArrayList<>() ;
        result.add(s) ;
        return result ;
    }

    public String PathToName(String path)
    {
        String result = path.replace('_',' ') ;
        return result.substring(0,result.length()-4) ;
    }
    public void AddSongsFromSongsFolderToDatabase()
    {
        File songsDir = new File(System.getProperty("user.dir") + "/songs") ;
        File[] files = songsDir.listFiles() ;

        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            Statement s = connection.createStatement() ;
            String query ;
            ResultSet resultSet ;
            for(int i = 0 ; i < files.length ; i++)
            {
                query = "SELECT name FROM songs WHERE path = '" + files[i].getName() + "';" ;
                resultSet = s.executeQuery(query) ;
                if(!resultSet.next()) {
                    query = "INSERT INTO songs (name,artist,album,genre,path) VALUES ";
                    s.executeUpdate(query + "('"+ PathToName(files[i].getName()) + "','artist" + String.valueOf(i) + "','album" + String.valueOf(i) + "','default genre','" + files[i].getName() + "');");
                }
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<User> GetUsersFromDatabase(boolean withAdmin)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        ArrayList<User> users = new ArrayList<>() ;

        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            Statement s1 = connection.createStatement() ;
            String query = "SELECT * FROM users" ;
            if(withAdmin == false)
                query += " WHERE id != " + adminUID + ";" ;
            ResultSet resultSet = s1.executeQuery(query) ;
            while (resultSet.next())
                users.add(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                )) ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users ;
    }

    public static void DeleteUser(int uid)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;

        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            String query = "DELETE FROM users where id = ?" ;
            PreparedStatement s1 = connection.prepareStatement(query) ;
            s1.setInt(1,uid);
            s1.executeUpdate();

            query = "SELECT playlist_id FROM playlists WHERE user_id = ?" ;
            PreparedStatement s2 = connection.prepareStatement(query) ;
            s2.setInt(1,uid);
            ResultSet resultSet = s2.executeQuery() ;
            while (resultSet.next())
            {
                int playlist_id = resultSet.getInt(1) ;
                query = "DROP TABLE playlist_" + playlist_id + ";" ;
                Statement s3 = connection.createStatement() ;
                s3.executeUpdate(query) ;
            }

            query = "DELETE FROM playlists WHERE user_id = ?" ;
            PreparedStatement s4 = connection.prepareStatement(query) ;
            s4.setInt(1,uid);
            s4.executeUpdate() ;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AddSongToPlaylistInDatabase(int playlist_id,int song_id)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        System.out.println("ADDING " + song_id + " TO PL NR " + playlist_id);
        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            Statement statement = connection.createStatement() ;
            String query = "INSERT INTO playlist_" + playlist_id + " (song_id) VALUES (" + song_id + ");" ;
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void RemoveSongFromPlaylistInDatabase(int playlist_id,int song_id)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
//        System.out.println("DELETING " + song_id + " FROM PL NR " + playlist_id);
        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            Statement statement = connection.createStatement() ;
            String query = "DELETE FROM playlist_" + playlist_id + " WHERE song_id = " + song_id + ";" ;
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Song GetSongWithId(int id)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String dbusername = "root" ;
        String dbpassword = "" ;
        System.out.println("RETRIEVING SONG " + id + "!");
        try {
            Connection connection = DriverManager.getConnection(dburl,dbusername,dbpassword) ;
            Statement s = connection.createStatement() ;
            String query = "SELECT * FROM songs where id = " + id + ";" ;
            ResultSet resultSet = s.executeQuery(query) ;
            if(resultSet.next())
                return new Song(
                        System.getProperty("user.dir") + "/songs/" + resultSet.getString(6),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        arrayOf(resultSet.getString(5)),
                        0,
                        resultSet.getInt(1),
                        app.musicPlayer
                );
            throw new RuntimeException("NO SONG WITH ID " + id + "!") ;
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null ;
    }
    public static PlayList GetPlaylistWithId(int id)
    {
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;
        String playlistName = "" ;
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            Statement s = connection.createStatement() ;
            String query = "SELECT name FROM playlists WHERE playlist_id = " + id + ";" ;
            ResultSet resultSet ;
            resultSet = s.executeQuery(query) ;
            if(resultSet.next()) {
                playlistName = resultSet.getString(1);
                query = "SELECT song_id FROM playlist_" + id + ";" ;
                resultSet = s.executeQuery(query) ;
                while (resultSet.next())
                    songs.add(GetSongWithId(resultSet.getInt(1))) ;
            }
            else
                throw new RuntimeException("THERE IS NO PLAYLIST WITH ID" + id) ;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return new PlayList(playlistName,id,songs) ;
    }
    public static ArrayList<Song> SearchForSong(String value)
    {
        ArrayList<Song> results = new ArrayList() ;
        String dburl = "jdbc:mysql://localhost:3306/fakespotify" ;
        String username = "root" ;
        String password = "" ;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dburl,username,password) ;
            Statement s = connection.createStatement() ;
            String query = "SELECT * FROM songs WHERE LOWER(name) LIKE '%" + value + "%' OR UPPER(name) LIKE '%" + value + "%' ;" ;
            ResultSet resultSet = s.executeQuery(query) ;

            while (resultSet.next())
                results.add(new Song(
                        resultSet.getString(6),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        arrayOf(resultSet.getString(5)),
                        0,
                        resultSet.getInt(1),
                        app.musicPlayer
                        )
                ) ;

            connection.close();

            return results;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}