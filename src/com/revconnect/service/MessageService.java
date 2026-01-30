package com.revconnect.service;

import com.revconnect.model.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private final String FILE = "messages.dat";

    public void send(Message m) {
        List<Message> list = getAll();
        list.add(m);
        saveAll(list);
    }

    public List<Message> getConversation(int u1, int u2) {
        List<Message> result = new ArrayList<Message>();
        for (Message m : getAll()) {
            if ((m.getFromId() == u1 && m.getToId() == u2) ||
                (m.getFromId() == u2 && m.getToId() == u1)) {
                result.add(m);
            }
        }
        return result;
    }

    public void markRead(int toId) {
        List<Message> list = getAll();
        for (Message m : list) {
            if (m.getToId() == toId) {
                m.markRead();
            }
        }
        saveAll(list);
    }

    private List<Message> getAll() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE));
            return (List<Message>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<Message>();
        }
    }

    private void saveAll(List<Message> list) {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving messages");
        }
    }
}
