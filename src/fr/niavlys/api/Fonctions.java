package fr.niavlys.api;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Fonctions {
	
	public String normalize(String str) {
		str = str.toLowerCase();
		str = str.replace('ê', 'e');
		str = str.replace('é', 'e');
		str = str.replace('è', 'e');
		str = str.replace('à', 'a');
		str = str.replace('â', 'a');
		str = str.replaceAll("'", "_");
		str = str.replace(' ', '_');
		return str;
	}
	
	public String capitalize(String str){  
		str = str.toLowerCase();
		str = str.replace('_', ' ');
	    String words[]=str.split("\\s");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}
	
	public ItemMeta setItemFlags(ItemMeta item) {
		for(ItemFlag itmf : ItemFlag.values()) {
			item.addItemFlags(itmf);
		}
		return item;
	}
	
	public ItemMeta setItemFlagsEnchant(ItemMeta item) {
		for(ItemFlag itmf : ItemFlag.values()) {
			item.addItemFlags(itmf);
		}
		item.addEnchant(Enchantment.DURABILITY, 1, false);
		return item;
	}
	
	public ItemStack getNoneItem() {
		ItemStack it = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(" ");
		setItemFlags(itM);
		it.setItemMeta(itM);
		
		return it;
	}
	
	public void si(Inventory inv, int fn) {
		ItemStack it = getNoneItem();
		
		int st = 0;

		while(st != fn) {
			inv.setItem(st, it);
			st++;
		}
	}

	public void errorMSG(Player p, String name, String ver, String nomerreur, String errmsg) {
		 p.sendMessage(ChatColor.RED+"["+name+" "+ver+"] - Erreur '"+nomerreur+"': "+errmsg);
		 return;
	}
	
	public void validationMSG(Player p, String name, String ver, String cmd, String valmsg) {
		p.sendMessage(ChatColor.GREEN+"["+name+" "+ver+"] - Validation '"+cmd+"': "+valmsg);
		return;
	}
	
	public void sendHelp(Player p, String command, String usage, List<String> args, ChatColor color) {
		p.sendMessage(color+"--- Help: (/"+command+") ---");
		p.sendMessage(color+"Usage: "+usage);
		Integer a = 0;
		Integer nb_arg = args.size();
		while(a != nb_arg){
			p.sendMessage(color+"Argument ("+a+"): "+args.get(a));
			a++;
		}
		p.sendMessage(color+"--- ---");
		return;
	}
}