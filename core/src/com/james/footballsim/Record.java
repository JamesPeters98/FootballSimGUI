package com.james.footballsim;

import java.util.ArrayList;

public class Record{
	
	ArrayList<RecordDetails> records;
	private boolean most;
	private int recordsToHold;
	private String recordName;
	
	// most indicates a most record. 
	public Record(boolean most, int recordsToHold, String recordName){
		this.most = most;
		this.recordsToHold = recordsToHold;
		records = new ArrayList<RecordDetails>();
		this.recordName = recordName;
	}
	
	public void checkRecord(int record, Team team){
		for(int i = 0; i < recordsToHold;i++){
			if(i < records.size()){
				int currentRecord = records.get(i).record;
				if(most){
					if(record >= currentRecord){
						//System.out.println(team.name+" set new record for "+recordName+": "+record+" > "+currentRecord);
						putNewRecord(record, team);
						break;
					}
				} else {
					if(record < currentRecord){
						putNewRecord(record, team);
						break;
					}
				}
			} else {
				//System.out.println("no record found");
				putNewRecord(record, team);
				break;
			}
		}
	}

	public String getRecordName() {
		return recordName;
	}
	
//	public void setNewRecord(int i, int record, Team team){
//		records.set(i, new RecordDetails(team, record));
//		System.out.println(team.name+" set new record for "+recordName+": "+record);
//	}
	
	public void putNewRecord(int record, Team team){
		if(records.size() >= recordsToHold){
			//System.out.println("Removing record at "+(records.size()-1));
			records.remove(recordsToHold-1);
			Utils.sortArrayByBestRecord(records);
			//System.out.println("Records size "+(records.size()));
			//printRecordTable();
		}
		records.add(new RecordDetails(team,record));
		Utils.sortArrayByBestRecord(records);
		//System.out.println(team.name+" set new record for "+recordName+": "+record);
		//System.out.println("Records size "+(records.size()));
		//printRecordTable();
	}
	
	public void printRecordTable(){
		System.out.format("%-2s%-25s%-10s\n", new String[]{"","Team",recordName});
		System.out.println("----------------------------------------------------------------------------------");
		Utils.sortArrayByBestRecord(records);
		for(int i = 0; i <records.size();i++) {
			RecordDetails stats = records.get(i);
		    System.out.format("%-2s%-25s%-10s\n", new String[]{""+(i+1),stats.team.name,""+stats.record});
		}
		System.out.println("");
	}

}

