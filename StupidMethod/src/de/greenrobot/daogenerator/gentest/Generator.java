package de.greenrobot.daogenerator.gentest;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public abstract class Generator {
	private String outPath = "src-gen";

	protected abstract void creat(Schema schema);

	public void generateAll(int version, String dir) {
		String dbroot = dir + ".db";

		Schema schema = new Schema(version, dbroot + ".bean");
		schema.setDefaultJavaPackageDao(dbroot + ".dao");
		schema.setDefaultJavaPackageTest(dbroot + ".test");
		creat(schema);

		File f = new File(outPath);

		if (!f.exists())
			f.mkdirs();

		try {
			new DaoGenerator().generateAll(schema, f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {

	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outpath) {
		this.outPath = outpath;
	}
}
