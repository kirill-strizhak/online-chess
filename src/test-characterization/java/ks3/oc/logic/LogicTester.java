package ks3.oc.logic;

import ks3.oc.Figure;
import ks3.oc.Protocol;
import ks3.oc.board.Board;
import ks3.oc.dialogs.FigurePickerWindow;
import ks3.oc.main.MainWindow;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public abstract class LogicTester {

    @Mock
    Board board;
    @Mock
    private MainWindow mainWindow;
    @Mock
    FigurePickerWindow figurePickerWindow;

    protected Logic logic;

    private boolean check = false;
    private boolean myTurn = true;
    private Figure draggedFigure;
    private int kingCol;
    private int kingRow;

    Figure[][] fig = {
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() },
            { new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure(), new Figure() }
    };

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initFigure(col(), row());
        when(board.isCheck()).thenAnswer((in) -> check);
        when(board.draggedFigure()).thenAnswer((in) -> draggedFigure);
        doCallRealMethod().when(board).moveAndClear(any(), anyInt(), anyInt());
        when(board.getKingCol(anyInt())).thenAnswer((in) -> kingCol);
        when(board.getKingRow(anyInt())).thenAnswer((in) -> kingRow);
        when(board.figureAt(anyInt(), anyInt())).thenAnswer((in) -> {
            int col = (Integer) in.getArguments()[0];
            int row = (Integer) in.getArguments()[1];
            return fig[col][row];
        });

        when(mainWindow.getMyColor()).thenAnswer((in) -> getMyColor());
        when(mainWindow.getOppColor()).thenAnswer((in) -> getOppColor());
        when(mainWindow.isMyTurn()).thenAnswer((in) -> myTurn);
        logic = new Logic(board, mainWindow, figurePickerWindow);
    }

    void initFigure(int col, int row) {
        fig[col][row].empty = false;
        fig[col][row].firstStep = true;
        fig[col][row].type = type();
        fig[col][row].color = getMyColor();
    }

    public void initEnemy(int col, int row) {
        initEnemy(col, row, type());
    }

    public void initEnemy(int col, int row, int type) {
        initSimple(col, row, getOppColor(), type);
    }

    public void initFriendly(int col, int row) {
        initFriendly(col, row, type());
    }

    public void initFriendly(int col, int row, int type) {
        initSimple(col, row, getMyColor(), type);
    }

    private void initSimple(int col, int row, int color, int type) {
        fig[col][row].empty = false;
        fig[col][row].type = type;
        fig[col][row].color = color;
    }

    void clearFigure(int col, int row) {
        fig[col][row].empty = true;
        fig[col][row].type = Protocol.NULL;
        fig[col][row].color = Protocol.NULL;
    }

    void validate(Set<String> expected) {
        validate(col(), row(), expected);
    }

    void validate(int col, int row, Set<String> expected) {
        logic.calculateAllowedMoves(col, row);
        Set<String> result = convertResult();
        assertTrue("Result does not contain all expected values: " + result, result.containsAll(expected));
        assertTrue("Result has unexpected values: " + result, expected.containsAll(result));
    }

    private Set<String> convertResult() {
        Set<String> result = new HashSet<>();
        for (int i = 0; logic.getAllowed()[i][0] != -1; i++) {
            result.add(logic.getAllowed()[i][0] + ":" + logic.getAllowed()[i][1]);
        }
        return result;
    }

    void setCheck(boolean check) {
        this.check = check;
    }

    void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    void setDraggedFigure(int col, int row) {
        this.draggedFigure = fig[col][row];
    }

    void setKingPosition(int col, int row) {
        kingCol = col;
        kingRow = row;
    }

    public int getMyColor() {
        return Protocol.WHITE;
    }

    public int getOppColor() {
        return Protocol.BLACK;
    }

    protected abstract int col();

    protected abstract int row();

    protected abstract int type();

}
