package io.github.wylan.captcha.item;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

@Getter
@RequiredArgsConstructor
public enum CaptchaItem {

    // -- Unicos --
    // Comidas
    APPLE("na maçã", new MaterialData(Material.APPLE, (byte) 0)),
    COOKIE("no biscoito", new MaterialData(Material.COOKIE, (byte) 0)),
    POTATO_ITEM("na batata", new MaterialData(Material.POTATO_ITEM, (byte) 0)),
    RAW_FISH("no peixe", new MaterialData(Material.RAW_FISH, (byte) 0)),
    CARROT_ITEM("na cenoura", new MaterialData(Material.CARROT_ITEM, (byte) 0)),
    // Objetos
    BOOK("no livro", new MaterialData(Material.BOOK, (byte) 0)),
    SKULL_ITEM("na cabeça", new MaterialData(Material.SKULL_ITEM, (byte) 3)),
    // Ferramentas
    IRON_PICKAXE("na picareta", new MaterialData(Material.IRON_PICKAXE, (byte) 0)),
    IRON_AXE("no machado", new MaterialData(Material.IRON_AXE, (byte) 0)),
    IRON_SPADE("na pá", new MaterialData(Material.IRON_SPADE, (byte) 0)),
    IRON_HOE("na enxada", new MaterialData(Material.IRON_HOE, (byte) 0)),
    // -- Multiplos --
    FOODS("nas comidas",
            Lists.newArrayList(
                    new MaterialData(Material.APPLE, (byte) 0),
                    new MaterialData(Material.COOKIE, (byte) 0)
            ),
            Lists.newArrayList(
                    new MaterialData(Material.POTATO_ITEM, (byte) 0),
                    new MaterialData(Material.RAW_FISH, (byte) 0),
                    new MaterialData(Material.CARROT_ITEM, (byte) 0)
            )
    ),
    TOOLS("nas ferramentas", Lists.newArrayList(
            new MaterialData(Material.IRON_PICKAXE, (byte) 0),
            new MaterialData(Material.IRON_SPADE, (byte) 0),
            new MaterialData(Material.IRON_HOE, (byte) 0)
    )),
    HEADS("nas cabeças", Lists.newArrayList(
            new MaterialData(Material.SKULL_ITEM, (byte) 0),
            new MaterialData(Material.SKULL_ITEM, (byte) 3)
    )),
    MULTIPLE_FISH("nos peixes", Lists.newArrayList(
            new MaterialData(Material.RAW_FISH, (byte) 0),
            new MaterialData(Material.RAW_FISH, (byte) 1),
            new MaterialData(Material.RAW_FISH, (byte) 2)
    ));

    private final String phrase;
    private final List<MaterialData> items;
    private final List<MaterialData> conflicts;

    CaptchaItem(String phrase, List<MaterialData> items) {
        this.phrase = phrase;
        this.items = items;
        this.conflicts = Lists.newArrayList();
    }

    CaptchaItem(String phrase, MaterialData item) {
        this.phrase = phrase;
        this.items = Lists.newArrayList(item);
        this.conflicts = Lists.newArrayList();
    }

    public MaterialData getRandomItem() {
        List<MaterialData> shuffledSet = Lists.newArrayList(this.items);
        Collections.shuffle(shuffledSet);
        return shuffledSet.stream().findFirst().orElse(null);
    }

}
