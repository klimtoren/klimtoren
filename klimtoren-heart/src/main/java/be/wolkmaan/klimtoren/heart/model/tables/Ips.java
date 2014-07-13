/**
 * This class is generated by jOOQ
 */
package be.wolkmaan.klimtoren.heart.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Ips extends org.jooq.impl.TableImpl<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> {

	private static final long serialVersionUID = -799300938;

	/**
	 * The singleton instance of <code>public.ips</code>
	 */
	public static final be.wolkmaan.klimtoren.heart.model.tables.Ips IPS = new be.wolkmaan.klimtoren.heart.model.tables.Ips();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> getRecordType() {
		return be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord.class;
	}

	/**
	 * The column <code>public.ips.id</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.ips.address</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord, java.lang.Object> ADDRESS = createField("address", org.jooq.impl.DefaultDataType.getDefaultDataType("cidr"), this, "");

	/**
	 * The column <code>public.ips.customtype</code>.
	 */
	public final org.jooq.TableField<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord, java.lang.String> CUSTOMTYPE = createField("customtype", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>public.ips</code> table reference
	 */
	public Ips() {
		this("ips", null);
	}

	/**
	 * Create an aliased <code>public.ips</code> table reference
	 */
	public Ips(java.lang.String alias) {
		this(alias, be.wolkmaan.klimtoren.heart.model.tables.Ips.IPS);
	}

	private Ips(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Ips(java.lang.String alias, org.jooq.Table<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, be.wolkmaan.klimtoren.heart.model.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord> getPrimaryKey() {
		return be.wolkmaan.klimtoren.heart.model.Keys.IPS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord>>asList(be.wolkmaan.klimtoren.heart.model.Keys.IPS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<be.wolkmaan.klimtoren.heart.model.tables.records.IpsRecord, ?>>asList(be.wolkmaan.klimtoren.heart.model.Keys.IPS__IPS_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public be.wolkmaan.klimtoren.heart.model.tables.Ips as(java.lang.String alias) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Ips(alias, this);
	}

	/**
	 * Rename this table
	 */
	public be.wolkmaan.klimtoren.heart.model.tables.Ips rename(java.lang.String name) {
		return new be.wolkmaan.klimtoren.heart.model.tables.Ips(name, null);
	}
}