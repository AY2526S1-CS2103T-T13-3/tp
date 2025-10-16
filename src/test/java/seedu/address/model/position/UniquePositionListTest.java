package seedu.address.model.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.position.exceptions.DuplicatePositionException;
import seedu.address.model.position.exceptions.PositionNotFoundException;

public class UniquePositionListTest {

    private final UniquePositionList uniquePositionList = new UniquePositionList();

    @Test
    public void contains_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.contains(null));
    }

    @Test
    public void contains_positionNotInList_returnsFalse() {
        assertFalse(uniquePositionList.contains(new Position("FW")));
    }

    @Test
    public void contains_positionInList_returnsTrue() {
        uniquePositionList.add(new Position("FW"));
        assertTrue(uniquePositionList.contains(new Position("FW")));
    }

    @Test
    public void contains_positionWithSameNameDifferentCase_returnsTrue() {
        uniquePositionList.add(new Position("FW"));
        assertTrue(uniquePositionList.contains(new Position("fw")));
    }

    @Test
    public void add_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.add(null));
    }

    @Test
    public void add_duplicatePosition_throwsDuplicatePositionException() {
        uniquePositionList.add(new Position("FW"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.add(new Position("FW")));
    }

    @Test
    public void add_duplicatePositionDifferentCase_throwsDuplicatePositionException() {
        uniquePositionList.add(new Position("FW"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.add(new Position("fw")));
    }

    @Test
    public void remove_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.remove(null));
    }

    @Test
    public void remove_positionDoesNotExist_throwsPositionNotFoundException() {
        assertThrows(PositionNotFoundException.class, () -> uniquePositionList.remove(new Position("FW")));
    }

    @Test
    public void remove_existingPosition_removesPosition() {
        uniquePositionList.add(new Position("FW"));
        uniquePositionList.remove(new Position("FW"));
        assertFalse(uniquePositionList.contains(new Position("FW")));
    }

    @Test
    public void getByName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.getByName(null));
    }

    @Test
    public void getByName_positionNotInList_throwsPositionNotFoundException() {
        assertThrows(PositionNotFoundException.class, () -> uniquePositionList.getByName("FW"));
    }

    @Test
    public void getByName_existingPosition_returnsPosition() {
        Position fw = new Position("FW");
        uniquePositionList.add(fw);
        assertEquals(fw, uniquePositionList.getByName("FW"));
    }

    @Test
    public void getByName_existingPositionDifferentCase_returnsPosition() {
        Position fw = new Position("FW");
        uniquePositionList.add(fw);
        assertEquals(fw, uniquePositionList.getByName("fw"));
        assertEquals(fw, uniquePositionList.getByName("Fw"));
    }

    @Test
    public void getByName_nameWithWhitespace_trimsAndReturnsPosition() {
        Position fw = new Position("FW");
        uniquePositionList.add(fw);
        assertEquals(fw, uniquePositionList.getByName(" FW "));
    }

    @Test
    public void setPositions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.setPositions(null));
    }

    @Test
    public void setPositions_list_replacesOwnListWithProvidedList() {
        uniquePositionList.add(new Position("FW"));
        List<Position> positionList = Collections.singletonList(new Position("GK"));
        uniquePositionList.setPositions(positionList);
        UniquePositionList expectedUniquePositionList = new UniquePositionList();
        expectedUniquePositionList.add(new Position("GK"));
        assertEquals(expectedUniquePositionList.asUnmodifiableObservableList(),
                uniquePositionList.asUnmodifiableObservableList());
    }

    @Test
    public void setPositions_listWithDuplicatePositions_throwsDuplicatePositionException() {
        List<Position> listWithDuplicatePositions = Arrays.asList(new Position("FW"), new Position("FW"));
        assertThrows(DuplicatePositionException.class, () ->
                uniquePositionList.setPositions(listWithDuplicatePositions));
    }

    @Test
    public void setPositions_listWithDuplicatePositionsDifferentCase_throwsDuplicatePositionException() {
        List<Position> listWithDuplicatePositions = Arrays.asList(new Position("FW"), new Position("fw"));
        assertThrows(DuplicatePositionException.class, () ->
                uniquePositionList.setPositions(listWithDuplicatePositions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniquePositionList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_iteratesThroughPositions() {
        uniquePositionList.add(new Position("FW"));
        uniquePositionList.add(new Position("GK"));

        int count = 0;
        for (Position position : uniquePositionList) {
            count++;
        }
        assertEquals(2, count);
    }
}
