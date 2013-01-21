import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager{
	private Set<Contact> contacts;
	private List<FutureMeeting> futureMeetings;
	private List<PastMeeting> pastMeetings;
	
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException{
		if(!this.contacts.containsAll(contacts) || (date.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) )
			throw new IllegalArgumentException ("The meeting is being tried to set in the past or any of the contacts is unknown/non-existent");
		FutureMeeting meeting = new FutureMeetingImpl(date,contacts);
		futureMeetings.add(meeting);
		return meeting.getId();
	}

	public PastMeeting getPastMeeting(int id) throws IllegalArgumentException{
		Iterator<FutureMeeting> it1 = futureMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				throw new IllegalArgumentException("The ID introduced pertains to a Future Meeting");
		}
		Iterator<PastMeeting> it2 = pastMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public FutureMeeting getFutureMeeting(int id) throws IllegalArgumentException{
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				throw new IllegalArgumentException("The ID introduced pertains to a Past Meeting");
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public Meeting getMeeting(int id) {
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while (it1.hasNext()){
			if (it1.next().getId() == id)
				return it1.next();
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while (it1.hasNext()){
			if(it2.next().getId() == id)
				return it2.next();
		}
		return null;
	}

	public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException {
		if(!contacts.contains(contact))
			throw new IllegalArgumentException ("The contact does not exists");
		List<Meeting> meetingsWithContact = new ArrayList<Meeting>();
		Iterator<FutureMeeting> it = futureMeetings.iterator();
		while(it.hasNext()){
			if (it.next().getContacts().contains(contact))
				meetingsWithContact.add(it.next());				
		}
		return meetingsWithContact;
	}

	@SuppressWarnings("unchecked")
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> unsortedList = new ArrayList<Meeting>();
		Iterator<PastMeeting> it1 = pastMeetings.iterator();
		while(it1.hasNext()){
			if(it1.next().getDate() == date)
				unsortedList.add(it1.next());
		}
		Iterator<FutureMeeting> it2 = futureMeetings.iterator();
		while(it2.hasNext()){
			if(it2.next().getDate() == date)
				unsortedList.add(it2.next());
		}
		return (List<Meeting>) sortListByDate(unsortedList);
	}
	
	@SuppressWarnings("unchecked")
	public List<PastMeeting> getPastMeetingList(Contact contact) throws IllegalArgumentException{
		if(!contacts.contains(contact)){
			throw new IllegalArgumentException ("The contact does not exist in the list of contacts");
		}
		List<Meeting> unsortedList = new ArrayList<Meeting>();
		Iterator<PastMeeting> it = pastMeetings.iterator();
		while (it.hasNext()){
			if (it.next().getContacts().contains(contact))
				unsortedList.add(it.next());
		}
		return (List<PastMeeting>) sortListByDate(unsortedList);
	}

	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws NullPointerException, IllegalArgumentException{
		if(contacts == null || date == null || text == null)
			throw new NullPointerException("Any of the arguments is null");
		if(contacts.isEmpty() || !this.contacts.containsAll(contacts))
			throw new IllegalArgumentException ("The list of contacts is empty or any of the contacts does not exist");
		this.pastMeetings.add(new PastMeetingImpl(date,contacts,text));
	}

	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}
	
	public void addNewContact(String name, String notes) throws NullPointerException{
		if (name == null || notes == null)
			throw new NullPointerException ("The name or the notes are null");
		contacts.add(new ContactImpl(name,notes));
	}

	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<Contact> getContacts(String name) throws NullPointerException{
		if (name == null)
			throw new NullPointerException ("The parameter name cannot be null");
		Set<Contact> contactsWithName = new HashSet<Contact>();
		Iterator<Contact> it = contacts.iterator();
		while(it.hasNext()){
			if(it.next().getName().contains(name)){
				contactsWithName.add(it.next());
			}
		}
		return contactsWithName;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
	// This private method is used for sorting an unsorted List of Meetings by date. 
	// Uses restricted wildcards accepting only the class Meeting and its subclasses
	private  List<? extends Meeting> sortListByDate(List<? extends Meeting> unsortedList){
		List<Meeting> sortedList = new ArrayList<Meeting>();
		while(!unsortedList.isEmpty()){
			Iterator<? extends Meeting> it = unsortedList.iterator();
			long oldestDate = it.next().getDate().getTimeInMillis();
			Meeting oldestM = it.next();
			while(it.hasNext()){
				if(oldestDate > it.next().getDate().getTimeInMillis()){
					oldestDate = it.next().getDate().getTimeInMillis();
					oldestM = it.next();
				}								
			}
			unsortedList.remove(oldestM);
			sortedList.add(oldestM);
		}
		return sortedList;
	}

}
