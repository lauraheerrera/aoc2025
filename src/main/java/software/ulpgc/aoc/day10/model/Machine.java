package software.ulpgc.aoc.day10.model;

import java.util.List;

public record Machine(long targetMask, List<Long> buttonMasks) {
    public int minPresses() {
        return findMinPresses(0, 0L, targetMask, buttonMasks, 0, 999999);
    }

    private int findMinPresses(int buttonIdx, long currentMask, long targetMask, List<Long> buttons, int currentPresses, int bestPresses) {
        return currentMask == targetMask
            ? Math.min(currentPresses, bestPresses)
            : (buttonIdx >= buttons.size() || currentPresses >= bestPresses
                ? bestPresses
                : findMinPresses(
                    buttonIdx + 1, 
                    currentMask ^ buttons.get(buttonIdx), 
                    targetMask, 
                    buttons, 
                    currentPresses + 1, 
                    findMinPresses(buttonIdx + 1, currentMask, targetMask, buttons, currentPresses, bestPresses)
                ));
    }
}
