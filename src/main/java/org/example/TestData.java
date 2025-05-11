package org.example;

import java.util.Arrays;
import java.util.List;

public class TestData {
    private final String text;
    private final String expectedLanguage;

    public TestData(String text, String expectedLanguage) {
        this.text = text;
        this.expectedLanguage = expectedLanguage;
    }

    public static List<TestData> getTestSamples() {
        return Arrays.asList(
                new TestData("Hello, world", "en"),
                new TestData("Привет, мир", "ru"),
                new TestData("Hallo, Welt", "de"),
                new TestData("Bonjour, le monde", "fr"),
                new TestData("Hola, mundo", "es"),

                new TestData("Great achievements often begin with tiny, consistent actions—a daily habit, a small " +
                        "act of courage, or a moment of learning. Just as rivers carve canyons over time, our repeated " +
                        "efforts shape our future. Whether mastering a skill, building relationships, or pursuing dreams," +
                        " progress comes not from sudden leaps but from showing up again and again. Today’s small step is" +
                        " tomorrow’s foundation. What will you start today?", "en"),
                new TestData("Хрустящий иней серебрится на пожухлой траве, а воздух звенит от прохлады. Солнце, ещё" +
                        " не набравшее силу, заливает парки медовым светом, превращая каждый лист в маленькое произведение" +
                        " искусства — багряные клёны, золотые берёзы, огненные рябины. Где-то вдалеке слышится смех детей," +
                        " собирающих каштаны, а над головой, разрезая небо, криком провожают на юг журавлиные клинья. В этом" +
                        " хрупком балансе между увяданием и красотой — вся поэзия русской осени.", "ru"),
                new TestData("Die Luft riecht nach feuchter Erde und reifen Pilzen, während das goldene Licht der " +
                        "tiefstehenden Sonne durch das bunte Blätterdach der Buchen und Eichen filtert. Jeder Schritt auf" +
                        " dem knisternden Laubteppich hallt wider, begleitet vom leisen Rascheln der Eichhörnchen, die emsig" +
                        " ihre Wintervorräte sammeln. Der Herbstnebel tanzt zwischen den Stämmen und hüllt den Wald in ein" +
                        " geheimnisvolles Leichttuch – eine stille Übergangszeit, in der die Natur ihren letzten farbenprächtigen" +
                        " Auftritt feiert, bevor der Winter Einzug hält.", "de"),
                new TestData("Un léger brouillard enveloppe les toits du village, estompant les contours des vieilles" +
                        " maisons de pierre. L'air frais sent les pommes mûres et les feuilles mortes, tandis que les rayons" +
                        " du soleil levant caressent doucement les vignes rougissantes. Quelques gouttes de rosée brillent" +
                        " comme des perles sur les toiles d'araignée tendues entre les branches dénudées. Au loin, le chant" +
                        " mélancolique d'un merle se mêle au crissement des pas sur le gravier – c'est l'automne dans toute" +
                        " sa grâce éphémère, une saison où le temps semble suspendu entre nostalgie et renaissance.", "fr"),
                new TestData("El sol, rojo como una granada madura, se hunde lentamente en el mar, tiñendo las olas" +
                        " de dorado y púrpura. En el puerto, las barcas de pesca balancean suavemente al compás de la brisa" +
                        " salada, mientras los gaviotas trazan círculos en el cielo como cometas vivientes. Las terrazas" +
                        " de los cafés se llenan del murmullo de conversaciones y el tintineo de vasos de vino; el aroma" +
                        " a azahar y pescado fresco flota en el aire. Es ese momento mágico donde el día, en su última" +
                        " exhalación, regala una postal de serenidad que parece detener el tiempo.", "es")
        );
    }

    public String getText() {
        return text;
    }

    public String getExpectedLanguage() {
        return expectedLanguage;
    }
}