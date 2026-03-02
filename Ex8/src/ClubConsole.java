
public class ClubConsole {

    private final BudgetLedger financialLedger;
    private final MinutesBook meetingMinutes;
    private final EventPlanner clubEvents;

    public ClubConsole(BudgetLedger financialLedger, MinutesBook meetingMinutes, EventPlanner clubEvents) {
        this.financialLedger = financialLedger;
        this.meetingMinutes = meetingMinutes;
        this.clubEvents = clubEvents;
    }

    public void run() {
        FinancialTool treasurerTool = new TreasurerTool(financialLedger);
        MinutesTool secretaryTool = new SecretaryTool(meetingMinutes);
        EventTool eventLead = new EventLeadTool(clubEvents);

        treasurerTool.addIncome(5000, "sponsor");
        secretaryTool.addMinutes("Meeting at 5pm");
        eventLead.createEvent("HackNight", 2000);

        System.out.println("Summary: ledgerBalance=" + financialLedger.balanceInt() + ", minutes=" + meetingMinutes.count() + ", events=" + eventLead.getEventsCount());
    }
}
