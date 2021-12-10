package application;

public class Person {

		String name;
		double posX;
		double posY;
		
		Person(String name, double x, double y) {
			this.name = name;
			this.posX = x;
			this.posY = y;
		}	
		
		Person(String name){
			this(name, 0, 0);
		}

		String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}

		double getPosX() {
			return posX;
		}

		void setPosX(double posX) {
			this.posX = posX;
		}

		double getPosY() {
			return posY;
		}

		void setPosY(double posY) {
			this.posY = posY;
		}

}//Person
