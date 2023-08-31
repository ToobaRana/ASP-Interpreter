package Test;

public class TestExpandLeadingTabs {

    static String expandLeadingTabs(String string) {

		int n = 0;
		int tabToBlank = 4-(n % 4);
		
		
		for (char s : string.toCharArray()){
			if (s == ' ') {
				n++;
			}
			else if (s == '\t'){

				for (int i = 0; i<tabToBlank; i++){
					//s = 'b';
                    System.out.println(s);
                    
				}
			}

            else{
                break;
            }
		}

		return string;
    }

    public static void main(String[] args) {
        
        System.out.println(expandLeadingTabs("a heiehi"));
    }
}
