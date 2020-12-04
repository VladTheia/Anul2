import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class prb3 {
    public static void main(String[] args) {
        User sub1 = new User();
        User sub2 = new User();
        User sub3 = new User();
        Channel channel = new Channel("Top8");
        channel.subscribe(sub1);
        channel.subscribe(sub2);
        channel.subscribe(sub3);

        channel.notify("New vid");

    }
}

class Channel {
    List<User> subscribers;
    String name;

    public Channel(String name) {
        subscribers = new ArrayList<User>();
        this.name = name;
    }

    public String getChannelName() {
        return name;
    }

    public void subscribe(User sub) {
        if (sub != null)
            subscribers.add(sub);
    }

    public void unsubscribe(User sub) {
        if (subscribers.contains(sub))
            subscribers.remove(sub);
    }

    public void notify(String notification) {
        Iterator<User> it = subscribers.iterator();
        while (it.hasNext()) {
            User sub = it.next();
            sub.update(this);
        }
    }
}

class User {
    public void update(Channel channel) {
        System.out.println("The channel " + channel.getChannelName() + " has uploaded a new video");
    }
}