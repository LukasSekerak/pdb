package sk.upjs.gursky.pdb;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sk.upjs.gursky.bplustree.BPTree;

public class UnclusteredBPTree extends BPTree<PersonStringKey, SurnameAndOffsetEntry> {

	public static final File INDEX_FILE = new File("person.unclastered.tree");
	public static final File INPUT_DATA_FILE = new File("person.tab");
	
	public UnclusteredBPTree() {
		super(SurnameAndOffsetEntry.class, INDEX_FILE);
	}
	
	
	public static UnclusteredBPTree createByBulkLoading() throws IOException {
		UnclusteredBPTree tree = new UnclusteredBPTree();
		long startTime = System.nanoTime();
		RandomAccessFile raf = new RandomAccessFile(INPUT_DATA_FILE, "r");
		
		FileChannel channel = raf.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
		List<SurnameAndOffsetEntry> entries = new ArrayList<>();
		long fileSize = INPUT_DATA_FILE.length();
				
		for (int offset = 0; offset < fileSize; offset += 4096) { 
			System.out.println("Processing page " + (offset / 4096));
			buffer.clear();
			channel.read(buffer, offset);
			buffer.rewind();
			int numberOfrecords = buffer.getInt();
					
			for (int i = 0; i < numberOfrecords; i++) {  
				PersonEntry entry = new PersonEntry();
				entry.load(buffer);
				long entryOffset = offset + 4 + i* entry.getSize();
				
				SurnameAndOffsetEntry item = new SurnameAndOffsetEntry(entry.surname, entryOffset);
				entries.add(item);
			}
		}
	
		Collections.sort(entries);
		tree.openAndBatchUpdate(entries.iterator(), entries.size());
		channel.close();
		raf.close();
		System.out.println("Index created in " + (System.nanoTime() - startTime)/1_000_000.0 + " ms");
		return tree;
	}
	
	public List<PersonEntry> unclusteredIntervalQuery(PersonStringKey low, PersonStringKey high) throws IOException {
		List<SurnameAndOffsetEntry> references = intervalQuery(low, high);
	
		RandomAccessFile raf = new RandomAccessFile(INPUT_DATA_FILE, "r");
		
		FileChannel channel = raf.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
		List<PersonEntry> result = new LinkedList<>();
		
		for (SurnameAndOffsetEntry ref : references) {
			buffer.rewind();
			long page = (int) ref.offset / 4096;
			channel.read(buffer, page * 4096);
			buffer.position((int)(ref.offset - (page * 4096)));
			
		    PersonEntry entry = new PersonEntry();
			entry.load(buffer);
			result.add(entry);
		}
		
		channel.close();
		raf.close();
		return result;
	}

}
