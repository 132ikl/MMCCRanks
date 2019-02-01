package club.moddedminecraft.mmccranks;

import club.moddedminecraft.mmccranks.Command.CommandRank;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Mod(modid = MMCCRanks.MODID, name = MMCCRanks.NAME, version = MMCCRanks.VERSION, acceptableRemoteVersions = "*")
public class MMCCRanks
{
    public static final String MODID = "mmccranks";
    public static final String NAME = "MMCC Ranks";
    public static final String VERSION = "1.0";

    public static String member_item;
    public static Properties properties;
    public static MinecraftServer server;
    public static File mod_config_dir;

    public static Map<UUID, Rank> player_ranks = new HashMap<>();
    public static List<Rank> rank_list = new ArrayList<Rank>();

    public static Rank default_rank = new Rank("default","", " <", "> ", "yellow", "blue", "gray", "gray");
    //public Rank member = new Rank("member","[Member]", " ", ": ", "dark_green", "gray", "gray", "gray");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        EventListener eventListener = new EventListener();
        MinecraftForge.EVENT_BUS.register(eventListener);
        mod_config_dir = event.getModConfigurationDirectory();
        loadConfig(mod_config_dir);
        importConfig();
        importPlayerRanks(mod_config_dir);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRank());
        server = event.getServer();
    }

    public static void exportPlayerRanks(File modConfigDir) {
        Properties rank_prop = new Properties();
        File ranks = new File(modConfigDir, "mmccranks_players.cfg");

        for(Map.Entry<UUID, Rank> entry : player_ranks.entrySet()) {
            UUID key = entry.getKey();
            Rank value = entry.getValue();
            rank_prop.setProperty(key.toString(),value.getName());
        }

        try (FileOutputStream ostream = new FileOutputStream(ranks)) {
            rank_prop.store(ostream, null);
        } catch (IOException e) {
            System.err.println("Error saving new player rank file!");
            e.printStackTrace();
        }
    }

    public void importPlayerRanks(File modConfigDir) {
        Properties rank_prop = new Properties();
        File ranks = new File(modConfigDir, "mmccranks_players.cfg");

        //Loads config if it exists or creates a default one if not
        if (ranks.exists() && ranks.isFile()) {
            try (FileInputStream istream = new FileInputStream(ranks)) {
                rank_prop.load(istream);
            } catch (IOException e) {
                System.err.println("Error loading player rank file!");
                e.printStackTrace();
            }
        }
        if (rank_prop.size() > 0) {
            Enumeration<String> enums = (Enumeration<String>) rank_prop.propertyNames();
            while (enums.hasMoreElements()) {
                String key = enums.nextElement();
                String value = rank_prop.getProperty(key);
                UUID player = UUID.fromString(key);
                for (Rank rank : MMCCRanks.rank_list) {
                    if (rank.getName().equals(value)) {
                        MMCCRanks.player_ranks.putIfAbsent(player, rank);
                    }
                }
            }
        }
    }

    public void importConfig() {
        if (properties.size() > 0) {
            Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();
            while (enums.hasMoreElements()) {
                String key = enums.nextElement();
                String value = properties.getProperty(key);
                Scanner s = new Scanner(value).useDelimiter(",");
                try {
                    Rank rank = new Rank(key, s.next(), s.next(), s.next(), s.next(), s.next(), s.next(), s.next());
                    if (rank.getName().equals("default")) {
                        rank_list.remove(default_rank);
                        default_rank = rank;
                    } else {
                        rank_list.add(rank);
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Rank configuration error! Using default configuration.");
                    rank_list.clear();
                    MMCCRanks.rank_list.add(default_rank);
                    break;
                }
            }
        }
    }

    public void loadConfig(File modConfigDir) {
        MMCCRanks.properties = new Properties();
        File config = new File(modConfigDir, "mmccranks.cfg");

        //Loads config if it exists or creates a default one if not
        if (config.exists() && config.isFile()) {
            try (FileInputStream istream = new FileInputStream(config)) {
                MMCCRanks.properties.load(istream);
            } catch (IOException e) {
                System.err.println("Error loading configuration file!");
                e.printStackTrace();
            }
        }else{
            MMCCRanks.properties.setProperty("member_item", "minecraft:diamond");
            MMCCRanks.properties.setProperty("member","[Member], ,: ,");
            try (FileOutputStream ostream = new FileOutputStream(config)) {
                MMCCRanks.properties.store(ostream, null);
            } catch (IOException e) {
                System.err.println("Error saving new configuration file!");
                e.printStackTrace();
            }
        }
    }
}
