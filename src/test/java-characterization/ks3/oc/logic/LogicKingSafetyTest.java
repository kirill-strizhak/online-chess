package ks3.oc.logic;

import ks3.oc.Protocol;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LogicKingSafetyTest extends LogicMoveTester {

    // x ~ ~ ~ x
    // ~ * ~ * ~
    // ~ ~ o ~ ~
    // ~ ~ ~ ~ ~
    // ~ ~ ~ ~ ~
    @Test
    public void testDetectsAttacksByPawns() {
        initEnemy(0, 0, Protocol.PAWN);
        initEnemy(4, 0, Protocol.PAWN);
        Set<String> expected = new HashSet<>(6);
        expected.addAll(Arrays.asList("2:1", "1:2", "3:2", "1:3", "2:3", "3:3"));
        validate(expected);
    }

    // ~ ~ ~ ~ ~
    // x * * * *
    // ~ ~ o ~ ~
    // * * * * x
    // ~ ~ ~ ~ ~
    @Test
    public void testDetectsAttacksByRooks_WhenHorizontal() {
        initEnemy(0, 1, Protocol.ROOK);
        initEnemy(4, 3, Protocol.ROOK);
        Set<String> expected = new HashSet<>(2);
        expected.addAll(Arrays.asList("1:2", "3:2"));
        validate(expected);
    }

    // ~ x ~ * ~
    // ~ * ~ * ~
    // ~ * o * ~
    // ~ * ~ * ~
    // ~ * ~ x ~
    @Test
    public void testDetectsAttacksByRooks_WhenVertical() {
        initEnemy(1, 0, Protocol.ROOK);
        initEnemy(3, 4, Protocol.ROOK);
        Set<String> expected = new HashSet<>(2);
        expected.addAll(Arrays.asList("2:1", "2:3"));
        validate(expected);
    }

    // x ~ ~ ~ ~
    // ~ ~ 1 ~ ~
    // ~ 1 o 2 ~
    // ~ ~ 2 ~ ~
    // ~ ~ ~ ~ x
    @Test
    public void testDetectsAttacksByKnights_WhenTopLeftAndBottomRight() {
        initEnemy(0, 0, Protocol.KNIGHT);
        initEnemy(4, 4, Protocol.KNIGHT);
        Set<String> expected = new HashSet<>(4);
        expected.addAll(Arrays.asList("1:1", "3:1", "1:3", "3:3"));
        validate(expected);
    }

    // ~ ~ ~ ~ x
    // ~ ~ 1 ~ ~
    // ~ 2 o 1 ~
    // ~ ~ 2 ~ ~
    // x ~ ~ ~ ~
    @Test
    public void testDetectsAttacksByKnights_WhenTopRightAndBottomLeft() {
        initEnemy(4, 0, Protocol.KNIGHT);
        initEnemy(0, 4, Protocol.KNIGHT);
        Set<String> expected = new HashSet<>(4);
        expected.addAll(Arrays.asList("1:1", "3:1", "1:3", "3:3"));
        validate(expected);
    }

    // ~ ~ * ~ ~
    // ~ 1 ~ 2 ~
    // x ~ o ~ x
    // ~ 1 ~ 2 ~
    // ~ ~ * ~ ~
    @Test
    public void testDetectsAttacksByBishops_WhenHorizontal() {
        initEnemy(0, 2, Protocol.BISHOP);
        initEnemy(4, 2, Protocol.BISHOP);
        Set<String> expected = new HashSet<>(4);
        expected.addAll(Arrays.asList("2:1", "1:2", "3:2", "2:3"));
        validate(expected);
    }

    // ~ ~ x ~ ~
    // ~ 1 ~ 1 ~
    // * ~ o ~ *
    // ~ 2 ~ 2 ~
    // ~ ~ x ~ ~
    @Test
    public void testDetectsAttacksByBishops_WhenVertical() {
        initEnemy(2, 0, Protocol.BISHOP);
        initEnemy(2, 4, Protocol.BISHOP);
        Set<String> expected = new HashSet<>(4);
        expected.addAll(Arrays.asList("2:1", "1:2", "3:2", "2:3"));
        validate(expected);
    }

    // ~ ~ ~ ~ ~
    // ~ ~ ~ ~ ~
    // ~ ~ ~ ~ ~
    // ~ ~ ~ ~ ~
    // ~ ~ ~ ~ ~
    @Override
    protected int col() {
        return 2;
    }

    @Override
    protected int row() {
        return 2;
    }

    @Override
    protected int type() {
        return Protocol.KING;
    }
}