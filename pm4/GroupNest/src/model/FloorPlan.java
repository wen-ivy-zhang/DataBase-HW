package model;

/*FloorPlanId INT UNSIGNED NOT NULL AUTO_INCREMENT,
NumberOfBedrooms INT NOT NULL,
NumberOfBathrooms INT NOT NULL,
*/
public class FloorPlan {
	private int floorPlanId;
	private int numberOfBedrooms;
	private int numberOfBathrooms;

	public FloorPlan(int floorPlanId, int numberOfBedrooms, int numberOfBathrooms) {
		this.floorPlanId = floorPlanId;
		this.numberOfBathrooms = numberOfBathrooms;
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public FloorPlan(int numberOfBedrooms, int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public FloorPlan(int floorPlanId) {
		this.floorPlanId = floorPlanId;
	}

	public int getFloorPlanId() {
		return floorPlanId;
	}

	public void setFloorPlanId(int floorPlanId) {
		this.floorPlanId = floorPlanId;
	}

	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}

	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floorPlanId;
		result = prime * result + numberOfBathrooms;
		result = prime * result + numberOfBedrooms;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloorPlan other = (FloorPlan) obj;
		if (floorPlanId != other.floorPlanId)
			return false;
		if (numberOfBathrooms != other.numberOfBathrooms)
			return false;
		if (numberOfBedrooms != other.numberOfBedrooms)
			return false;
		return true;
	}

	
}
