package main.java.model;

public class Action {

    private Pion selectedPion;
    private Coordonnee moveDirection;
    private Pion targetPion;

    public Action(Pion selectedPion, Coordonnee moveDirection, Pion targetPion) {
        this.selectedPion = selectedPion;
        this.moveDirection = moveDirection;
        this.targetPion = targetPion;
    }

    public Pion getSelectedPion() {
        return selectedPion;
    }

    public void setSelectedPion(Pion selectedPion) {
        this.selectedPion = selectedPion;
    }

    public Coordonnee getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(Coordonnee moveDirection) {
        this.moveDirection = moveDirection;
    }

    public Pion getTargetPion() {
        return targetPion;
    }

    public void setTargetPion(Pion targetPion) {
        this.targetPion = targetPion;
    }

    @Override
    public String toString() {
        return "Action{" +
                "selectedPion=" + selectedPion +
                ", moveDirection=" + moveDirection +
                ", targetPion=" + targetPion +
                '}';
    }
}
