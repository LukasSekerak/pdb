package sk.upjs.gursky.pdb;

import sk.upjs.gursky.bplustree.BPObject;

import java.nio.ByteBuffer;

public class SalaryOffsetEntry implements BPObject<SalaryKey, SalaryOffsetEntry> {

    private static final long serialVersionUID = -343652960720370085L;

    int salary;
    long offset;

    public SalaryOffsetEntry () { }

    public SalaryOffsetEntry (int salary, long offset) {
        this.salary = salary;
        this.offset = offset;
    }
    @Override
    public void load(ByteBuffer bb) {
        salary = bb.getInt();
        offset = bb.getLong();
    }

    @Override
    public void save(ByteBuffer bb) {
        bb.putInt(salary);
        bb.putLong(offset);
    }

    @Override
    public int getSize() {
        return 12;
    }

    @Override
    public SalaryKey getKey() {
        return new SalaryKey(salary);
    }

    @Override
    public int compareTo(SalaryOffsetEntry o) {
        return Integer.compare(salary, o.salary);
    }

    @Override
    public String toString() {
        return "SalaryOffsetEntry [salary=" + salary + ", offset=" + offset + "]";
    }
}
