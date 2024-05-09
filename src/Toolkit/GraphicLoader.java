/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Toolkit;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author oscar
 */
public class GraphicLoader {

    public static BufferedImage gameIcon;
    public static BufferedImage[] bubbles;
    public static BufferedImage[] map;
    public static BufferedImage[] mapCover;
    public static BufferedImage[] playerImage;
    public static BufferedImage[] smokeImage;
    public static BufferedImage[] acornImage;
    public static BufferedImage[] subItemIcon;
    public static BufferedImage[] displayCircle;
    public static BufferedImage[] gameMenuButton;
    public static BufferedImage[] closeLabel;
    public static BufferedImage[] xToCloseLabel;
    public static BufferedImage[] gameInventory;
    public static BufferedImage[] gameMenu;
    public static BufferedImage[] gameStats;
    public static BufferedImage[] gameNuts;
    public static BufferedImage[] gameInventoryIconLabel;
    public static BufferedImage[] gameInventoryIcon;
    public static BufferedImage[] reviveLabel;
    public static BufferedImage[] mysteryBox;
    public static BufferedImage[] mysteryBoxFilterIcon;
    public static BufferedImage[] mysteryBoxConfetti;
    public static BufferedImage[] mysteryBoxLootIcon;
    public static BufferedImage[] mysteryBoxNoneValueIcon;
    public static BufferedImage[] bearImage;
    public static BufferedImage[] deadBearImage;
    public static BufferedImage[] standingBearImage;
    public static BufferedImage[] birdImage;
    public static BufferedImage[] deadBirdImage;
    public static BufferedImage[] fallingBirdImage;
    public static BufferedImage[] birdPoopImage;
    public static BufferedImage[] eagleImage;
    public static BufferedImage[] fallingEagleImage;
    public static BufferedImage[] deadEagleImage;
    public static BufferedImage[] foxImage;
    public static BufferedImage[] deadFoxImage;
    public static BufferedImage[] musquitoImage;
    public static BufferedImage[] deadMusquitoImage;
    public static BufferedImage[] rabbitImage;
    public static BufferedImage[] deadRabbitImage;
    public static BufferedImage[] hedgehogImage;
    public static BufferedImage[] deadHedgehogImage;
    public static BufferedImage[] rightHedgehogImage;
    public static BufferedImage[] rightDeadHedgehogImage;
    public static BufferedImage[] hedgehogProjectileImage;
    public static BufferedImage[] lightningImage;
    public static BufferedImage[] waterImage;
    public static BufferedImage[] mudImage;
    public static BufferedImage[] stoneImage;
    public static BufferedImage[] towerImage;
    public static BufferedImage[] bulletImage;
    public static BufferedImage[] bulletFlameImage;
    public static BufferedImage[] prizeWheel;
    public static BufferedImage[] prizeWheelBackground;
    public static BufferedImage[] prizeWheelHolder;
    public static BufferedImage[] prizeWheelPointerTriangle;
    public static BufferedImage[] prizeWheelButton;
    public static BufferedImage[] prizeWheelStatsDisplay;
    public static BufferedImage[] prizeWheelDisplay;
    public static BufferedImage[] prizeWheelConfetti;
    public static BufferedImage[] abilityItem;
    public static BufferedImage[] abilitySubItem;
    public static BufferedImage[] abilityPriceDisplayer;
    public static BufferedImage[] abilityMouseMovedAnimation;
    public static BufferedImage[] abilityItemOption;
    public static BufferedImage[] abilityCircle;
    public static BufferedImage[] abilityButton;
    public static BufferedImage[] abilityOptionDisplay;
    public static BufferedImage[] abilityOptionOpenButton;
    public static BufferedImage[] abilityBackButton;
    public static BufferedImage[] abilitySmallItem;
    public static BufferedImage[] abilityChoseButton;
    public static BufferedImage[] abilityLimitLabel;
    public static BufferedImage[] console;
    public static BufferedImage[] consoleShadow;
    public static BufferedImage[] mainMenu;
    public static BufferedImage[] mainMenuLabel;
    public static BufferedImage[] mainStatLabel;
    public static BufferedImage[] mainScrollLabel;
    public static BufferedImage[] mainButton;
    public static BufferedImage[] shopItem;
    public static BufferedImage[] shopChart;
    public static BufferedImage[] shopNutDisplayer;

    public static BufferedImage[] optionDisplay;
    public static BufferedImage[] optionCloseButton;
    public static BufferedImage[] magazineButton;

    public static void loadGameGraphic() {
        try {
            gameIcon = ImageIO.read(GraphicLoader.class.getResource("/GameMenuPngs/gameIcon.png"));
            bubbles = spriteSheetLoader("/PlayerPngs/", 302, 102, 2, 2, 200, 50, "bubbels.png");
            map = spriteSheetLoader("/MapPngs/", 900, 1000, 3, 1, 900, 1000, "map.png");
            mapCover = spriteSheetLoader("/MapPngs/", 900, 145, 1, 1, 900, 145, "cover.png");
            playerImage = spriteSheetLoader("/PlayerPngs/", 300, 250, 13, 1, 250, 200, "player.png");
            smokeImage = spriteSheetLoader("/PlayerPngs/", 500, 350, 4, 1, 400, 300, "smoke.png");
            acornImage = spriteSheetLoader("/PlayerPngs/", 40, 40, 1, 1, 30, 30, "acorn.png");
            subItemIcon = spriteSheetLoader("/StartMenuPngs/", 200, 200, 27, 1, 80, 80, "subItem.png");
            displayCircle = spriteSheetLoader("/GameMenuPngs/", 40, 40, 1, 1, 20, 20, "displayCircle.png");
            gameMenuButton = spriteSheetLoader("/GameMenuPngs/", 400, 100, 1, 2, 400, 100, "exitButton.png");
            closeLabel = spriteSheetLoader("/GameMenuPngs/", 500, 500, 1, 1, 500, 500, "inGameCloseLabel.png");
            xToCloseLabel = spriteSheetLoader("/GameMenuPngs/", 40, 40, 1, 1, 40, 40, "x.png");
            gameInventory = spriteSheetLoader("/GameMenuPngs/", 1980, 180, 1, 1, 1930, 180, "inventory.png");
            gameMenu = spriteSheetLoader("/GameMenuPngs/", 316, 160, 2, 1, 316, 160, "InGameMeny.png");
            gameStats = spriteSheetLoader("/GameMenuPngs/", 120, 120, 2, 2, 72, 72, "stats.png");
            gameNuts = spriteSheetLoader("/NutPngs/", 40, 40, 2, 3, 40, 40, "nuts.png");
            gameInventoryIconLabel = spriteSheetLoader("/GameMenuPngs/", 120, 120, 3, 1, 120, 120, "inventoryIconLabel.png");
            gameInventoryIcon = spriteSheetLoader("/GameMenuPngs/", 60, 60, 3, 1, 60, 60, "disadvantageIcon.png");
            reviveLabel = spriteSheetLoader("/GameMenuPngs/", 200, 200, 1, 1, 200, 200, "reviveLabel.png");
            mysteryBoxConfetti = spriteSheetLoader("/MysteryBoxPngs/", 40, 40, 3, 3, 40, 40, "gameConfetti.png");
            mysteryBoxLootIcon = spriteSheetLoader("/MysteryBoxPngs/", 60, 60, 2, 3, 60, 60, "displayIcons.png");
            mysteryBoxNoneValueIcon = spriteSheetLoader("/MysteryBoxPngs/", 60, 60, 3, 2, 60, 60, "icons.png");
            mysteryBox = spriteSheetLoader("/MysteryBoxPngs/", 80, 80, 2, 2, 80, 80, "mysteryBox.png");
            mysteryBoxFilterIcon = spriteSheetLoader("/MysteryBoxPngs/", 70, 70, 2, 1, 70, 70, "iconPadding.png");
            bearImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 642, 402, 3, 4, 400, 250, "bears.png");
            deadBearImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 632, 395, 1, 1, 400, 250, "deadBear.png");
            standingBearImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 397, 634, 3, 2, 397, 634, "standingBear.png");
            birdImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 184, 212, 3, 3, 182, 212, "birds.png");
            deadBirdImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 182, 212, 1, 1, 182, 212, "deadBird.png");
            fallingBirdImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 184, 212, 2, 1, 184, 212, "fallingBird.png");
            birdPoopImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 102, 102, 2, 2, 102, 102, "birdPoop.png");
            towerImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 302, 502, 2, 1, 302, 502, "tower.png");
            bulletImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 70, 40, 1, 1, 30, 10, "bullet.png");
            bulletFlameImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 70, 40, 1, 1, 90, 50, "bulletFire.png");
            eagleImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 302, 202, 3, 3, 302, 202, "eagle.png");
            fallingEagleImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 300, 200, 1, 1, 300, 200, "fallingEagle.png");
            deadEagleImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 300, 200, 1, 1, 300, 200, "deadEagle.png");
            foxImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 252, 152, 3, 3, 300, 190, "foxes.png");
            deadFoxImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 250, 150, 1, 1, 300, 190, "deadFox.png");
            hedgehogImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 202, 102, 2, 2, 202, 102, "hedgehogLeft.png");
            deadHedgehogImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 200, 100, 1, 1, 200, 100, "deadHedgehog.png");
            rightHedgehogImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 202, 102, 2, 2, 202, 102, "hedgehogRight.png");
            rightDeadHedgehogImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 200, 100, 1, 1, 200, 100, "deadHedgehogRight.png");
            hedgehogProjectileImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 90, 60, 1, 1, 70, 40, "hedgehogProjectile.png");
            lightningImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 280, 868, 2, 1, 150, 3000, "lightning.png");
            waterImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 477, 68, 1, 3, 502, 72, "water.png");
            mudImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 800, 90, 1, 3, 600, 70, "mud.png");
            stoneImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 120, 125, 1, 1, 120, 125, "stone.png");
            musquitoImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 72, 72, 3, 2, 100, 100, "musquito.png");
            deadMusquitoImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 70, 70, 1, 1, 100, 100, "deadMusquito.png");
            rabbitImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 202, 152, 2, 4, 202, 152, "rabbit.png");
            deadRabbitImage = spriteSheetLoader("/ObstaclePngsLevelOne/", 200, 150, 1, 1, 200, 150, "deadRabbit.png");
            prizeWheel = spriteSheetLoader("/PrizeWheelPngs/", 500, 500, 2, 2, 500, 500, "prizeWheel.png");
            prizeWheelBackground = spriteSheetLoader("/PrizeWheelPngs/", 900, 700, 2, 1, 900, 700, "wheelBackground.png");
            prizeWheelHolder = spriteSheetLoader("/PrizeWheelPngs/", 200, 130, 1, 1, 200, 130, "wheelHolder.png");
            prizeWheelPointerTriangle = spriteSheetLoader("/PrizeWheelPngs/", 50, 80, 1, 1, 50, 80, "pointer.png");
            prizeWheelButton = spriteSheetLoader("/PrizeWheelPngs/", 170, 80, 2, 2, 170, 80, "button.png");
            prizeWheelStatsDisplay = spriteSheetLoader("/PrizeWheelPngs/", 300, 80, 1, 1, 300, 80, "holder.png");
            prizeWheelDisplay = spriteSheetLoader("/PrizeWheelPngs/", 850, 150, 1, 1, 850, 150, "displayer.png");
            prizeWheelConfetti = spriteSheetLoader("/PrizeWheelPngs/", 60, 60, 3, 2, 60, 60, "confetti.png");
            abilityItem = spriteSheetLoader("/StartMenuPngs/", 360, 360, 9, 1, 360, 360, "abilityItem.png");
            abilitySubItem = spriteSheetLoader("/StartMenuPngs/", 200, 200, 27, 1, 200, 200, "subItem.png");
            abilityPriceDisplayer = spriteSheetLoader("/StartMenuPngs/", 300, 60, 1, 1, 300, 60, "priceDisplayer.png");
            abilityMouseMovedAnimation = spriteSheetLoader("/StartMenuPngs/", 110, 200, 3, 1, 110, 204, "mouseMoved.png");
            abilityItemOption = spriteSheetLoader("/StartMenuPngs/", 500, 160, 1, 1, 500, 160, "itemOption.png");
            abilityCircle = spriteSheetLoader("/StartMenuPngs/", 200, 200, 1, 1, 200, 200, "buyAnimation.png");
            abilityButton = spriteSheetLoader("/StartMenuPngs/", 120, 50, 2, 1, 120, 50, "abilityButton.png");
            abilityOptionDisplay = spriteSheetLoader("/StartMenuPngs/", 400, 900, 1, 1, 400, 900, "optionDisplay.png");
            abilityOptionOpenButton = spriteSheetLoader("/StartMenuPngs/", 300, 120, 1, 1, 300, 120, "optionButton.png");
            abilityBackButton = spriteSheetLoader("/StartMenuPngs/", 200, 80, 1, 1, 200, 80, "backButton.png");
            abilitySmallItem = spriteSheetLoader("/StartMenuPngs/", 180, 180, 9, 1, 120, 120, "smallAbilityItem.png");
            abilityChoseButton = spriteSheetLoader("/StartMenuPngs/", 120, 40, 1, 2, 120, 40, "choseButton.png");
            abilityLimitLabel = spriteSheetLoader("/StartMenuPngs/", 300, 40, 1, 1, 300, 150, "limitDisplay.png");
            console = spriteSheetLoader("/StartMenuPngs/", 400, 120, 1, 1, 400, 120, "console.png");
            consoleShadow = spriteSheetLoader("/StartMenuPngs/", 425, 140, 1, 1, 425, 140, "consoleShadow.png");
            mainMenu = spriteSheetLoader("/StartMenuPngs/", 1920, 1080, 1, 1, 1920, 1080, "mainMenu.png");
            mainMenuLabel = spriteSheetLoader("/StartMenuPngs/", 900, 120, 1, 1, 900, 120, "mainMenuLabel.png");
            mainStatLabel = spriteSheetLoader("/StartMenuPngs/", 530, 830, 1, 1, 530, 830, "statLabel.png");
            mainScrollLabel = spriteSheetLoader("/StartMenuPngs/", 400, 150, 2, 1, 400, 150, "scrollHelp.png");
            mainButton = spriteSheetLoader("/StartMenuPngs/", 600, 90, 1, 2, 600, 90, "button.png");
            shopItem = spriteSheetLoader("/StartMenuPngs/", 360, 360, 11, 1, 360, 360, "shopItem.png");
            shopChart = spriteSheetLoader("/StartMenuPngs/", 300, 300, 4, 1, 200, 200, "chart.png");
            shopNutDisplayer = spriteSheetLoader("/StartMenuPngs/", 500, 150, 1, 1, 500, 150, "nutDisplayer.png");
            optionDisplay = spriteSheetLoader("/GameAbilitiesPngs/", 300, 450, 1, 1, 300, 450, "optionDisplay.png");
            optionCloseButton = spriteSheetLoader("/GameAbilitiesPngs/", 250, 60, 1, 1, 250, 60, "closeButton.png");

            magazineButton = spriteSheetLoader("/GameAbilitiesPngs/", 300, 100, 1, 1, 300, 100, "magazineButton.png");
        } catch (IOException e) {
        }
    }

    private static BufferedImage[] spriteSheetLoader(String search, int spriteWidth, int spriteHeight, int colX, int rowY, int newWidth, int newHeight, String filename) throws IOException {
        String sökväg = search + filename;
        BufferedImage spriteSheet = ImageIO.read(GraphicLoader.class.getResource(sökväg));
        BufferedImage[] sprites = new BufferedImage[colX * rowY];
        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < colX; x++) {
                sprites[(colX * y) + x] = spriteSheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        for (int i = 0; i < sprites.length; i++) {
            BufferedImage inputImage = sprites[i];
            Image scaledImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = outputImage.createGraphics();
            outputImage = graphics2D.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);
            graphics2D.dispose();
            graphics2D = outputImage.createGraphics();
            graphics2D.drawImage(scaledImage, 0, 0, null);
            graphics2D.dispose();
            sprites[i] = outputImage;
        }
        return sprites;
    }

}
