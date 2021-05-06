package in.matka.ns.Intefaces;

import java.util.ArrayList;

import in.matka.ns.Model.GameModel;
import in.matka.ns.Model.GameStatusModel;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 09,April,2021
 */
public interface OnGetAllGames {
    void onGetAllGames(ArrayList<GameStatusModel> gList);
}
