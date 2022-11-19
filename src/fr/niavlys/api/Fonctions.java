package fr.niavlys.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.niavlys.api.db.DbConnection;

public class Fonctions {
	
	public void BddUpdate(String table, HashMap<Integer, String> valuesName, HashMap<Integer, Object> values, String whereName, String whereVal) {
		String psS;
		Integer arg = 0;
		Integer arg2 = 0;
		Integer nb_values = values.size();
		psS = "UPDATE " + table + " SET ";
		while(arg != nb_values) {
			arg = arg+1;
			if(arg == nb_values) {
				psS = psS + valuesName.get(arg) + " = ?";
			}
			else {
				psS = psS + valuesName.get(arg) + " = ?, ";
			}
		}
		if(whereName != null) {
			psS = psS + " WHERE " + whereName + " = ?";
		}
		try {
			final DbConnection dbConnection = NvApi.getDatabaseManager().getDbConnection();
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement ps = connection.prepareStatement(psS);
			while(arg2 != nb_values) {
				arg2 = arg2+1;
				if(values.get(arg2) instanceof String) {
					ps.setString(arg2, values.get(arg2).toString());
				}
				if(values.get(arg2) instanceof Integer) {
					ps.setInt(arg2, Integer.valueOf(values.get(arg2).toString()));
				}
				if(values.get(arg2) instanceof Timestamp) {
					ps.setTimestamp(arg2, Timestamp.valueOf(values.get(arg2).toString()));
				}
			}
			if(whereVal != null) {
				ps.setString(nb_values+1, whereVal);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void BddInsert(String table, HashMap<Integer,Object> values) {
		String psS;
		Integer arg = 0;
		Integer nb_values = values.size();
		psS = "INSERT INTO " + table + " VALUES(";
		while(arg != nb_values) {
			arg++;
			if(arg == nb_values) {
				psS = psS + "?)";
			}
			else {
				psS = psS + "?, ";
			}
		}
		arg = 0;
		final DbConnection dbConnection = NvApi.getDatabaseManager().getDbConnection();
		try {
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement ps = connection.prepareStatement(psS);
			while(arg != nb_values) {
				arg++;
				if(values.get(arg) instanceof String) {
					ps.setString(arg, values.get(arg).toString());
				}
				if(values.get(arg) instanceof Integer) {
					ps.setInt(arg, Integer.valueOf(values.get(arg).toString()));
				}
				if(values.get(arg) instanceof Timestamp) {
					ps.setTimestamp(arg, Timestamp.valueOf(values.get(arg).toString()));
				}
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public void BddDelete(String table, String whereName, String whereVal) {
		final DbConnection dbConnection = NvApi.getDatabaseManager().getDbConnection();
		try {
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement ps = connection.prepareStatement("DELETE FROM "+table+" WHERE "+whereName+" = ?");
			ps.setString(1, whereVal);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public ResultSet BddSelect(String table, HashMap<Integer, String> values, String whereName, String whereVal) {
		String psS = "SELECT ";
		Integer arg = 0;
		Integer nb_values = values.size();
		while(arg != nb_values) {
			arg++;
			if(arg == nb_values) {
				psS = psS + values.get(arg) + " ";
			}
			else {
				psS = psS + values.get(arg) + ", ";
			}
		}
		psS = psS + "FROM " + table;
		if(whereName != null) {
			psS = psS + " WHERE "+whereName+" = ?";
		}
		final DbConnection dbConnection = NvApi.getDatabaseManager().getDbConnection();
		try {
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement ps = connection.prepareStatement(psS);
			if(whereVal != null) {
				ps.setString(1, whereVal);
			}
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	
	public ItemMeta setItemFlags(ItemMeta item) {
		for(ItemFlag itmf : ItemFlag.values()) {
			item.addItemFlags(itmf);
		}
		return item;
	}
	
	public ItemStack getNoneItem() {
		ItemStack it = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(" ");
		itM.addEnchant(Enchantment.DURABILITY, 1, true);
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