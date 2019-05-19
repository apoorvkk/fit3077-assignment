package edu.monash.it.fit3077.akql.model.health.TobaccoUse;

/*
This interface is mainly used to separate views from TobaccoUseModel and restrict access to that model by strictly
defining the contract here.
 */
public interface TobaccoUseModelInterface {
    /**
     * Return the textual status recorded for tobacco use.
     * @return the status.
     */
    String getStatus();
}
