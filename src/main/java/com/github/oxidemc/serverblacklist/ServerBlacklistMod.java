package com.github.oxidemc.serverblacklist;

import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class ServerBlacklistMod implements ClientModInitializer {
  private static Logger logger = LogManager.getLogger("Server Blacklist");
  private static String blacklist;

  public static Logger logger() {
    return logger;
  }

  public static String blacklist() {
    return blacklist;
  }

  @Override
  public void onInitializeClient() {
    try {
      URL url = new URL("https://raw.githubusercontent.com/OxideMC/malicious-server-blacklist/main/blacklist.json");
      InputStreamReader reader = new InputStreamReader(url.openStream());
      List<String> bl = new Gson().fromJson(reader, List.class);

      StringBuilder sb = new StringBuilder("(");

      bl.forEach((str) -> {
        sb.append(str + "|");
      });

      sb.deleteCharAt(sb.length() - 1);
      sb.append(")");

      blacklist = sb.toString();
      
      System.out.println(blacklist);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
