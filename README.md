# JMA

JMA - Java Mastodon API - is an API for developing Mastodon bots in Java and Kotlin

This library is a fork of the [Mastodon4j](https://github.com/sys1yagi/mastodon4j) library.

# Usage

It has never been that easy to programm your own Mastodon bot.<br>
Thanks to the Event-Listener-Approach, it is simple to reply to events.<br>

# Installation

# Creating a bot

Java
``` java
public static void main(String[] args) {
    try {    
        MastodonClient client = new MastodonClient.Builder("your-instance.example", new OkHttpClient.Builder(), new Gson())
            .accessToken("YOUR_TOKEN")
            .addEventListener(new ExampleEventListener)
            .build();
    } catch (Mastodon4jRequestException exception) {
        exception.printStackTrace();
    }
}
```

Kotlin
``` kotlin
fun main(args: Array<String>) {
    try {
        val client = MastodonClient.Builder("your-instance.example", OkHttpClient.Builder(), Gson())
            .accessToken("YOUR_TOKEN")
            .addEventListener(ExampleEventListener())
            .build()
    } catch (exception: Mastodon4jRequestException) {
        exception.printStackTrace()
    }
}
```

# Creating a Listener

Java
``` java
public class FollowNotificationListener implements NotificationListener {

    @Override
    public void onFollowNotification(NotificationEvent event) {
        MastodonClient client = event.getClient();
        Notification notification = event.getNotification();
        String user = notification.getAccount().getAcct();
        String userAsMention = "@" + user;
        String replyContent = "Welcome on board " + userAsMention + "!";

        try {
            new Statuses(client).postStatus(replyContent, null, null, false, null).execute();
        } catch (Mastodon4jRequestException exception) {
            exception.printStackTrace();
        }
    }
}
```

# License

All code and assets until and including the commit [b7fbb565](https://github.com/sys1yagi/mastodon4j/commit/b7fbb565abd024ce113e3a6f0caf2eb9bbc10fc7) are licensed under the [MIT License](LICENSE).<br>
The additional code and assets since then are licensed under the [CC BY-NC-SA 4.0 License](https://creativecommons.org/licenses/by-nc-sa/4.0/)