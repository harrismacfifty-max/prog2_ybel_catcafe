package catcafe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testklasse für {@link CatCafe}.
 *
 * <p>Die Tests prüfen die öffentliche Schnittstelle der Klasse CatCafe:
 *
 * <ul>
 *   <li>Wie viele Katzen befinden sich im Café?
 *   <li>Können Katzen hinzugefügt werden?
 *   <li>Können Katzen über ihren Namen gefunden werden?
 *   <li>Können Katzen über einen Gewichtsbereich gefunden werden?
 *   <li>Wie verhält sich die Klasse bei ungültigen oder nicht vorhandenen Eingaben?
 * </ul>
 *
 * <p>Alle Testfälle folgen dem Schema given - when - then:
 *
 * <ul>
 *   <li>given: Ausgangssituation herstellen
 *   <li>when: zu testende Methode aufrufen
 *   <li>then: erwartetes Ergebnis prüfen
 * </ul>
 */
class CatCafeTest {

    @Test
    void givenEmptyCafe_whenGetCatCount_thenReturnsZero() {
        // given
        // Ein neues CatCafe wird erzeugt.
        // Es wurde noch keine Katze hinzugefügt.
        CatCafe cafe = new CatCafe();

        // when
        // Die Anzahl der aktuell gespeicherten Katzen wird abgefragt.
        long count = cafe.getCatCount();

        // then
        // In einem leeren Café muss die Anzahl 0 sein.
        assertEquals(0, count);
    }

    @Test
    void givenEmptyCafe_whenAddOneCat_thenCatCountIsOne() {
        // given
        // Ein leeres CatCafe und eine einzelne Katze werden vorbereitet.
        CatCafe cafe = new CatCafe();
        FelineOverLord morticia = new FelineOverLord("Morticia", 3);

        // when
        // Die Katze wird dem Café hinzugefügt.
        cafe.addCat(morticia);

        // then
        // Danach muss genau eine Katze im Café gespeichert sein.
        assertEquals(1, cafe.getCatCount());
    }

    @Test
    void givenEmptyCafe_whenAddSeveralCatsWithDifferentWeights_thenCatCountMatchesAddedCats() {
        // given
        // Ein leeres CatCafe wird vorbereitet.
        CatCafe cafe = new CatCafe();

        // when
        // Drei Katzen mit unterschiedlichen Gewichten werden hinzugefügt.
        // Unterschiedliche Gewichte sind hier wichtig, weil die Katzen im Projekt
        // über ihre natürliche Ordnung, also vermutlich über compareTo(), verwaltet werden.
        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(new FelineOverLord("Gwenapurr Esmeralda", 3));
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        // then
        // Die Anzahl der Katzen muss der Anzahl der hinzugefügten Katzen entsprechen.
        assertEquals(3, cafe.getCatCount());
    }

    @Test
    void givenCafe_whenAddNullCat_thenThrowsNullPointerException() {
        // given
        // Ein CatCafe wird vorbereitet.
        CatCafe cafe = new CatCafe();

        // when + then
        // Es wird geprüft, dass das Hinzufügen von null nicht erlaubt ist.
        // Erwartet wird eine NullPointerException.
        // Dieser Test ist relevant, weil null sonst später zu schwerer auffindbaren Fehlern führen könnte.
        assertThrows(NullPointerException.class, () -> cafe.addCat(null));
    }

    @Test
    void givenCafeWithCats_whenGetCatByExistingName_thenReturnsMatchingCat() {
        // given
        // Ein CatCafe mit mehreren Katzen wird vorbereitet.
        // Die Katze "Morticia" wird in einer Variablen gespeichert,
        // damit später geprüft werden kann, ob exakt dieses Objekt zurückgegeben wird.
        CatCafe cafe = new CatCafe();
        FelineOverLord morticia = new FelineOverLord("Morticia", 3);
        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(morticia);
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        // when
        // Es wird nach einer Katze mit einem existierenden Namen gesucht.
        FelineOverLord result = cafe.getCatByName("Morticia");

        // then
        // assertSame prüft Objektidentität.
        // Es soll also nicht nur irgendeine gleiche Katze gefunden werden,
        // sondern genau die vorher eingefügte Instanz morticia.
        assertSame(morticia, result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByUnknownName_thenReturnsNull() {
        // given
        // Ein CatCafe mit Katzen wird vorbereitet.
        // Die gesuchte Katze "Unknown Cat" wird nicht hinzugefügt.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Morticia", 3));
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        // when
        // Es wird nach einem Namen gesucht, der im Café nicht existiert.
        FelineOverLord result = cafe.getCatByName("Unknown Cat");

        // then
        // Wenn keine passende Katze gefunden wird, soll null zurückgegeben werden.
        assertNull(result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByNullName_thenReturnsNull() {
        // given
        // Ein CatCafe mit einer Katze wird vorbereitet.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Morticia", 3));

        // when
        // Es wird mit null als Name gesucht.
        FelineOverLord result = cafe.getCatByName(null);

        // then
        // Dieser Test prüft das Verhalten bei ungültiger Sucheingabe.
        // Erwartet wird hier null, also kein Treffer.
        assertNull(result);
    }

    @Test
    void givenEmptyCafe_whenGetCatByName_thenReturnsNull() {
        // given
        // Ein leeres CatCafe wird vorbereitet.
        CatCafe cafe = new CatCafe();

        // when
        // Es wird nach einem Namen gesucht, obwohl keine Katzen vorhanden sind.
        FelineOverLord result = cafe.getCatByName("Morticia");

        // then
        // In einem leeren Café kann keine Katze gefunden werden.
        assertNull(result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightInRange_thenReturnsMatchingCat() {
        // given
        // Ein CatCafe mit mehreren Katzen unterschiedlicher Gewichte wird vorbereitet.
        // Gwen hat das Gewicht 3 und liegt damit im Suchbereich [3, 4).
        CatCafe cafe = new CatCafe();
        FelineOverLord gwen = new FelineOverLord("Gwenapurr Esmeralda", 3);
        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(gwen);
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        // when
        // Es wird nach einer Katze im Gewichtsbereich von 3 inklusive bis 4 exklusiv gesucht.
        FelineOverLord result = cafe.getCatByWeight(3, 4);

        // then
        // Gwen ist die passende Katze und soll exakt zurückgegeben werden.
        assertSame(gwen, result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightUsesInclusiveLowerLimit_thenReturnsCatAtMinWeight() {
        // given
        // Eine Katze mit Gewicht 2 wird eingefügt.
        // Der spätere Suchbereich beginnt ebenfalls bei 2.
        CatCafe cafe = new CatCafe();
        FelineOverLord sooky = new FelineOverLord("Miss Chief Sooky", 2);
        cafe.addCat(sooky);
        cafe.addCat(new FelineOverLord("Morticia", 3));

        // when
        // Gesucht wird im Bereich [2, 3).
        FelineOverLord result = cafe.getCatByWeight(2, 3);

        // then
        // Die untere Grenze ist inklusive.
        // Eine Katze mit genau minWeight = 2 muss also gefunden werden.
        assertSame(sooky, result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightUsesExclusiveUpperLimit_thenDoesNotReturnCatAtMaxWeight() {
        // given
        // Eine Katze mit Gewicht 5 wird eingefügt.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));

        // when
        // Gesucht wird im Bereich [3, 5).
        // Die 5 ist dabei die obere Grenze.
        FelineOverLord result = cafe.getCatByWeight(3, 5);

        // then
        // Die obere Grenze ist exklusiv.
        // Eine Katze mit genau maxWeight = 5 darf also nicht gefunden werden.
        assertNull(result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightWithNegativeMinWeight_thenReturnsNull() {
        // given
        // Ein CatCafe mit einer gültigen Katze wird vorbereitet.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Morticia", 3));

        // when
        // Es wird mit einer negativen unteren Gewichtsgrenze gesucht.
        FelineOverLord result = cafe.getCatByWeight(-1, 4);

        // then
        // Negative Gewichtsgrenzen sind fachlich ungültig.
        // Erwartet wird daher kein Treffer.
        assertNull(result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightWithMaxSmallerThanMin_thenReturnsNull() {
        // given
        // Ein CatCafe mit einer Katze wird vorbereitet.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Morticia", 3));

        // when
        // Es wird ein ungültiger Bereich übergeben:
        // minWeight ist größer als maxWeight.
        FelineOverLord result = cafe.getCatByWeight(5, 3);

        // then
        // Ein solcher Bereich kann keine gültige Katze enthalten.
        // Erwartet wird daher null.
        assertNull(result);
    }

    @Test
    void givenCafeWithCats_whenGetCatByWeightWithNoMatchingCat_thenReturnsNull() {
        // given
        // Ein CatCafe mit zwei Katzen wird vorbereitet.
        // Beide Katzen sind leichter als der spätere Suchbereich.
        CatCafe cafe = new CatCafe();
        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(new FelineOverLord("Morticia", 3));

        // when
        // Es wird nach Katzen im Bereich [8, 10) gesucht.
        FelineOverLord result = cafe.getCatByWeight(8, 10);

        // then
        // Keine vorhandene Katze liegt in diesem Bereich.
        // Deshalb muss null zurückgegeben werden.
        assertNull(result);
    }
}