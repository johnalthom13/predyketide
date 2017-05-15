package utils;

import java.util.Comparator;

import sp.PredyketideCompoundResult;

public class ResultRankingByNPScore implements Comparator<PredyketideCompoundResult>{

	@Override
	public int compare(PredyketideCompoundResult arg0, PredyketideCompoundResult arg1) {
		return (arg0.getNP_LikenessScore() >arg1.getNP_LikenessScore() ? -1 : (arg0.getNP_LikenessScore() == arg1.getNP_LikenessScore() ? 0 : 1));
	}

}


