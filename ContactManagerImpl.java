import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.lang.IllegalAccessException;

public class ContactManagerImpl implements ContactManager{
	private Set<Contact> contacts;
	private List<FutureMeeting> futureMeetings;
	private List<PastMeeting> pastMeetings;
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalAccessException{
		if(!this.contacts.containsAll(contacts) || (date.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) )
			throw new IllegalAccessException ("The meeting is being tried to set in the past or any of the contacts is unknown/non-existent");
		FutureMeeting meeting = new FutureMeetingImpl(date,contacts);
		futureMeetings.add(meeting);
		return meeting.getId();
	}

	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String next) {
		// TODO Auto-generated method stub
		
	}

	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}
	
	public void addNewContact(String name, String notes) {
		// TODO Auto-generated method stub
		
	}

	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}
	

}