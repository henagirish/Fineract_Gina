/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.organisation.office.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.office.exception.CannotUpdateOfficeWithParentOfficeSameAsSelf;
import org.apache.fineract.organisation.office.exception.RootOfficeParentCannotBeUpdated;
import org.joda.time.LocalDate;
import org.omg.PortableInterceptor.INACTIVE;

@Entity
@Table(name = "m_office", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }, name = "name_org"),
		@UniqueConstraint(columnNames = { "external_id" }, name = "externalid_org") })
public class Office extends AbstractPersistableCustom<Long> {

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private List<Office> children = new LinkedList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Office parent;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "hierarchy", nullable = true, length = 50)
	private String hierarchy;

	@Column(name = "opening_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date openingDate;

	@Column(name = "external_id", length = 100)
	private String externalId;

	/*
	 * Adding CIN Company Name Company Status Net Owned Fund RoC Registration
	 * Number Date of Incorporation Registered Address Modified on Aug 29.2019
	 * by Aiswarya Rajendran
	 * 
	 */

	@Column(name = "cin", nullable = false, length = 100)
	private String cin;

	@Column(name = "company_name", nullable = false, length = 100)
	private String companyName;

	@Column(name = "company_status", nullable = false, length = 100)
	private String companyStatus;

	@Column(name = "funds", nullable = false, length = 100)
	private Integer funds;

	@Column(name = "roc", length = 100)
	private String roc;

	@Column(name = "registration_number", length = 100)
	private Integer registrationNumber;

	@Column(name = "incorporated_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date incorporatedDate;

	@Column(name = "registration_address", nullable = false, length = 100)
	private String registrationAddress;

	public String getCin() {
		return this.cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return this.companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public Integer getFunds() {
		return this.funds;
	}

	public void setFunds(Integer funds) {
		this.funds = funds;
	}

	public String getRoc() {
		return this.roc;
	}

	public void setRoc(String roc) {
		this.roc = roc;
	}

	public Integer getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(Integer registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Date getIncorporatedDate() {
		return this.incorporatedDate;
	}

	public void setIncorporatedDate(Date incorporatedDate) {
		this.incorporatedDate = incorporatedDate;
	}

	public String getRegistrationAddress() {
		return this.registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public static Office headOffice(final String name, final LocalDate openingDate, final String externalId,
			final String cin, final String roc, final String companyName, final String companyStatus,
			final Integer registrationNumber, final String registrationAddress, final Integer funds,
			final LocalDate incorporatedDate) {
		return new Office(null, name, openingDate, externalId, cin, roc, companyName, companyStatus, registrationNumber,
				registrationAddress, funds, incorporatedDate);
	}

	public static Office fromJson(final Office parentOffice, final JsonCommand command) {

		final String name = command.stringValueOfParameterNamed("name");
		final LocalDate openingDate = command.localDateValueOfParameterNamed("openingDate");
		final String externalId = command.stringValueOfParameterNamed("externalId");
		/** 
		 * **/
		final String cin = command.stringValueOfParameterNamed("cin");
		final LocalDate incorporatedDate = command.localDateValueOfParameterNamed("incorporatedDate");
		final String roc = command.stringValueOfParameterNamed("roc");
		final String companyName = command.stringValueOfParameterNamed("companyName");
		final String companyStatus = command.stringValueOfParameterNamed("companyStatus");
		final String registrationAddress = command.stringValueOfParameterNamed("registrationAddress");
		// need to change cin to integer
		final int funds = command.integerValueOfParameterNamed(cin);
		final int registrationNumber = command.integerValueOfParameterNamed(cin);
		// Need to convert funds and registrationNumber
		// command.

		return new Office(parentOffice, name, openingDate, externalId, cin, roc, companyName, companyStatus,
				registrationNumber, registrationAddress, funds, incorporatedDate);
	}

	protected Office() {
		this.openingDate = null;
		this.parent = null;
		this.name = null;
		this.externalId = null;
		this.cin = null;
		this.roc = null;
		this.companyName = null;
		this.companyStatus = null;
		this.registrationNumber = null;
		this.registrationAddress = null;
		this.funds = null;
		this.incorporatedDate = null;
	}

	@SuppressWarnings("unused")
	private Office(final Office parent, final String name, final LocalDate openingDate, final String externalId,
			final String cin, final String roc, final String companyName, final String companyStatus,

	final Integer registrationNumber, final String registrationAddress, final Integer funds,
			final LocalDate incorporatedDate) {
		this.parent = parent;
		this.openingDate = openingDate.toDateTimeAtStartOfDay().toDate();
		if (parent != null) {
			this.parent.addChild(this);
		}

		if (StringUtils.isNotBlank(name)) {
			this.name = name.trim();
		} else {
			this.name = null;
		}
		if (StringUtils.isNotBlank(externalId)) {
			this.externalId = externalId.trim();
		} else {
			this.externalId = null;
		}

		/**/

		if (StringUtils.isNotBlank(cin)) {
			this.cin = cin.trim();
		} else {
			this.cin = null;
		}

		if (StringUtils.isNotBlank(roc)) {
			this.roc = roc.trim();
		} else {
			this.roc = null;
		}

		if (StringUtils.isNotBlank(companyName)) {
			this.companyName = companyName.trim();
		} else {
			this.companyName = null;
		}

		if (StringUtils.isNotBlank(companyStatus)) {
			this.companyStatus = name.trim();
		} else {
			this.companyStatus = null;
		}

		this.incorporatedDate = incorporatedDate.toDateTimeAtStartOfDay().toDate();

		if (StringUtils.isNotBlank(registrationAddress)) {
			this.registrationAddress = registrationAddress.trim();
		} else {
			this.registrationAddress = null;
		}
	}

	//

	private void addChild(final Office office) {
		this.children.add(office);
	}

	public Map<String, Object> update(final JsonCommand command) {

		final Map<String, Object> actualChanges = new LinkedHashMap<>(7);

		final String dateFormatAsInput = command.dateFormat();
		final String localeAsInput = command.locale();

		final String parentIdParamName = "parentId";

		if (command.parameterExists(parentIdParamName) && this.parent == null) {
			throw new RootOfficeParentCannotBeUpdated();
		}

		if (this.parent != null && command.isChangeInLongParameterNamed(parentIdParamName, this.parent.getId())) {
			final Long newValue = command.longValueOfParameterNamed(parentIdParamName);
			actualChanges.put(parentIdParamName, newValue);
		}

		final String openingDateParamName = "openingDate";
		if (command.isChangeInLocalDateParameterNamed(openingDateParamName, getOpeningLocalDate())) {
			final String valueAsInput = command.stringValueOfParameterNamed(openingDateParamName);
			actualChanges.put(openingDateParamName, valueAsInput);
			actualChanges.put("dateFormat", dateFormatAsInput);
			actualChanges.put("locale", localeAsInput);

			final LocalDate newValue = command.localDateValueOfParameterNamed(openingDateParamName);
			this.openingDate = newValue.toDate();
		}

		final String nameParamName = "name";
		if (command.isChangeInStringParameterNamed(nameParamName, this.name)) {
			final String newValue = command.stringValueOfParameterNamed(nameParamName);
			actualChanges.put(nameParamName, newValue);
			this.name = newValue;
		}

		final String externalIdParamName = "externalId";
		if (command.isChangeInStringParameterNamed(externalIdParamName, this.externalId)) {
			final String newValue = command.stringValueOfParameterNamed(externalIdParamName);
			actualChanges.put(externalIdParamName, newValue);
			this.externalId = StringUtils.defaultIfEmpty(newValue, null);
		}

		return actualChanges;
	}

	public boolean isOpeningDateBefore(final LocalDate baseDate) {
		return getOpeningLocalDate().isBefore(baseDate);
	}

	public boolean isOpeningDateAfter(final LocalDate activationLocalDate) {
		return getOpeningLocalDate().isAfter(activationLocalDate);
	}

	public LocalDate getOpeningLocalDate() {
		LocalDate openingLocalDate = null;
		if (this.openingDate != null) {
			openingLocalDate = LocalDate.fromDateFields(this.openingDate);
		}
		return openingLocalDate;
	}

	public void update(final Office newParent) {

		if (this.parent == null) {
			throw new RootOfficeParentCannotBeUpdated();
		}

		if (identifiedBy(newParent.getId())) {
			throw new CannotUpdateOfficeWithParentOfficeSameAsSelf(getId(), newParent.getId());
		}

		this.parent = newParent;
		generateHierarchy();
	}

	public boolean identifiedBy(final Long id) {
		return getId().equals(id);
	}

	public void generateHierarchy() {

		if (this.parent != null) {
			this.hierarchy = this.parent.hierarchyOf(getId());
		} else {
			this.hierarchy = ".";
		}
	}

	private String hierarchyOf(final Long id) {
		return this.hierarchy + id.toString() + ".";
	}

	public String getName() {
		return this.name;
	}

	public String getHierarchy() {
		return this.hierarchy;
	}

	public Office getParent() {
		return this.parent;
	}

	public boolean hasParentOf(final Office office) {
		boolean isParent = false;
		if (this.parent != null) {
			isParent = this.parent.equals(office);
		}
		return isParent;
	}

	public boolean doesNotHaveAnOfficeInHierarchyWithId(final Long officeId) {
		return !hasAnOfficeInHierarchyWithId(officeId);
	}

	private boolean hasAnOfficeInHierarchyWithId(final Long officeId) {

		boolean match = false;

		if (identifiedBy(officeId)) {
			match = true;
		}

		if (!match) {
			for (final Office child : this.children) {
				final boolean result = child.hasAnOfficeInHierarchyWithId(officeId);

				if (result) {
					match = result;
					break;
				}
			}
		}

		return match;
	}

	public void loadLazyCollections() {
		this.children.size();
	}
}