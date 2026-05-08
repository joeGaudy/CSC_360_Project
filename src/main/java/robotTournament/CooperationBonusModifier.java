package robotTournament;

public class CooperationBonusModifier extends GameModifier {

    private int bonusAmount;

    public CooperationBonusModifier(Game wrappedGame, int bonusAmount) {
        super(wrappedGame);
        this.bonusAmount = bonusAmount;
    }

    @Override
    public void playGame(Robot p1, Robot p2) {
        wrappedGame.playGame(p1, p2);

        if (p2.getOppsPrevDecision().equals("Cooperate")) {
            p1.setScore(p1.getScore() + bonusAmount);
            p1.setTotalScore(p1.getTotalScore() + bonusAmount);
        }

        if (p1.getOppsPrevDecision().equals("Cooperate")) {
            p2.setScore(p2.getScore() + bonusAmount);
            p2.setTotalScore(p2.getTotalScore() + bonusAmount);
        }

        notifyMoveObserver("Cooperation bonus modifier applied.");
    }
}