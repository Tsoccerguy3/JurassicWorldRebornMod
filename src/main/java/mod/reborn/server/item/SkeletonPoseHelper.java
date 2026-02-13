package mod.reborn.server.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mod.reborn.server.dinosaur.Dinosaur;
import mod.reborn.server.tabula.TabulaModelHelper;
import net.ilexiconn.llibrary.client.model.tabula.container.TabulaModelContainer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Utility methods for working with skeleton poses.
 */
public class SkeletonPoseHelper {
    /**
     * Returns a list of pose names for the given dinosaur by reading its skeleton JSON.
     */
    public static List<String> getPoseNames(Dinosaur dino) {
        String name = getDinoPathName(dino);
        String path = "/assets/rebornmod/models/entities/" + name + "/skeleton/" + name + "_skeleton.json";

        try (InputStream in = SkeletonPoseHelper.class.getResourceAsStream(path)) {
            if (in != null) {
                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(new InputStreamReader(in)).getAsJsonObject();
                JsonObject poses = root.getAsJsonObject("poses");
                Set<String> names = new LinkedHashSet<>();
                if (poses != null) {
                    String prefix = name + "_skeleton_";
                    for (Map.Entry<String, JsonElement> entry : poses.entrySet()) {
                        JsonArray arr = entry.getValue().getAsJsonArray();
                        for (JsonElement el : arr) {
                            JsonObject poseObj = el.getAsJsonObject();
                            String poseFile = poseObj.get("pose").getAsString();
                            String poseName = poseFile.startsWith(prefix) ? poseFile.substring(prefix.length()) : poseFile;
                            names.add(poseName);
                        }
                    }
                }
                if (!names.isEmpty()) {
                    return new ArrayList<>(names);
                }
            }
        } catch (Exception ignored) {
        }
        List<String> fallback = new ArrayList<>();
        fallback.add("idle");
        return fallback;
    }

    /**
     * Loads Tabula model containers for each pose defined in the dinosaur's skeleton JSON.
     * The returned list order matches {@link #getPoseNames(Dinosaur)}.
     */
    public static List<TabulaModelContainer> getPoseModels(Dinosaur dino) {
        String name = getDinoPathName(dino);
        String base = "/assets/rebornmod/models/entities/" + name + "/skeleton/";
        String jsonPath = base + name + "_skeleton.json";

        List<TabulaModelContainer> models = new ArrayList<>();
        try (InputStream in = SkeletonPoseHelper.class.getResourceAsStream(jsonPath)) {
            if (in != null) {
                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(new InputStreamReader(in)).getAsJsonObject();
                JsonObject poses = root.getAsJsonObject("poses");
                if (poses != null) {
                    Set<String> poseFiles = new LinkedHashSet<>();
                    for (Map.Entry<String, JsonElement> entry : poses.entrySet()) {
                        JsonArray arr = entry.getValue().getAsJsonArray();
                        for (JsonElement el : arr) {
                            JsonObject poseObj = el.getAsJsonObject();
                            poseFiles.add(poseObj.get("pose").getAsString());
                        }
                    }
                    for (String poseFile : poseFiles) {
                        try {
                            TabulaModelContainer container = TabulaModelHelper.loadTabulaModel(base + poseFile);
                            if (container != null) {
                                models.add(container);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }

        if (models.isEmpty()) {
            try {
                TabulaModelContainer fallback = TabulaModelHelper.loadTabulaModel(base + name + "_skeleton_idle");
                if (fallback != null) {
                    models.add(fallback);
                }
            } catch (Exception ignored) {
            }
        }

        return models;
    }

    private static String getDinoPathName(Dinosaur dino) {
        return dino.getName().toLowerCase(Locale.ENGLISH).replace(' ', '_');
    }
}
