/*
 *   Copyright (C) 2014-2016 GeorgH93
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package at.pcgamingfreaks.MarriageMaster.Bukkit.Databases;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

import at.pcgamingfreaks.MarriageMaster.Bukkit.MarriageMaster;

public class Language
{
	private static final int LANG_VERSION = 18;

	private final MarriageMaster plugin;
	private FileConfiguration lang;

	public Language(MarriageMaster marriagemaster) 
	{
		plugin = marriagemaster;
		loadFile();
	}
	
	public String get(String Option)
	{
		String msg = lang.getString("Language." + Option);
		if(msg != null)
		{
			msg = ChatColor.translateAlternateColorCodes('&', msg);
		}
		return msg == null ? "" : msg;
	}
	
	public void reload()
	{
		loadFile();
	}
	
	private void loadFile()
	{
		File file = new File(plugin.getDataFolder() + File.separator + "Lang", plugin.config.GetLanguage()+".yml");
		if(!file.exists())
		{
			extractLangFile(file);
		}
		lang = YamlConfiguration.loadConfiguration(file);
		updateLangFile(file);
	}
	
	private void extractLangFile(File Target)
	{
		try
		{
			plugin.saveResource("Lang" + File.separator + plugin.config.GetLanguage() + ".yml", true);
		}
		catch(Exception ex)
		{
			try
			{
				File file_en = new File(plugin.getDataFolder() + File.separator + "Lang", "en.yml");
				if(!file_en.exists())
				{
					plugin.saveResource("Lang" + File.separator + "en.yml", true);
				}
				Files.copy(file_en, Target);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private boolean updateLangFile(File file)
	{
		plugin.log.info("Language Version: " + lang.getInt("Version") + " => " + ((lang.getInt("Version") >= LANG_VERSION) ? "no " : "") + "update needed");
		if(lang.getInt("Version") != LANG_VERSION)
		{
			if(plugin.config.GetLanguageUpdateMode().equalsIgnoreCase("overwrite"))
			{
				extractLangFile(file);
				loadFile();
				plugin.log.info(get("Console.LangUpdated"));
				return true;
			}
			else
			{
				switch(lang.getInt("Version"))
				{
					case 1:
						lang.set("Language.Console.LangUpdated",		"Language File has been updated.");
						lang.set("Language.Priest.UnMadeYouAPriest",	"%s has fired you as a priest.");
						lang.set("Language.Priest.UnMadeAPriest",		"You have fired %s as a priest.");
						lang.set("Language.Ingame.PvPIsOff",			"You can't hurt your Partner if you have PvP disabled.");
					case 2:
						lang.set("Language.Ingame.ListeningStarted",	"You are now listening to the marry chat.");
						lang.set("Language.Ingame.ListeningStoped",		"You are no longer listening to the marry chat.");
						lang.set("Language.Description.ListenChat",		"Shows all chatmessages sent from a married player to his partner.");
					case 3:
						lang.set("Language.Ingane.PriestMarryOff",		"Priest (%s) is now offline, wedding called off.");
						lang.set("Language.Ingane.PlayerMarryOff",		"%s is now offline, wedding called off.");
						lang.set("Language.Ingane.PlayerCalledOff",		"%s called the wedding off.");
						lang.set("Language.Ingane.YouCalledOff",		"You have called the wedding off.");
						lang.set("Language.Priest.Confirm",				"Accept the marriage with /marry accept or deny it with /marry deny");
						lang.set("Language.Priest.AlreadyAccepted",		"You have already accepted the marriage.");
						lang.set("Language.Priest.NoRequest",			"There is no open marriage request.");
						lang.set("Language.Dialog.DoYouWant",			"Do you %1$s, want to marry %2$s on this server?");
						lang.set("Language.Dialog.AndDoYouWant",		"And do you %1$s, want to marry %2$s on this server?");
						lang.set("Language.Dialog.Married",				"I now pronounce you husband and wife. You may now kiss .");
						lang.set("Language.Dialog.YesIWant",			"Yes, I will!");
						lang.set("Language.Dialog.NoIDontWant",			"No, I will not!");
					case 4:
						lang.set("Language.Priest.NotYourSelf",			"You are not allowed to marry yourself.");
						lang.set("Language.Ingame.NoItemInHand",		"You are not holding an item.");
						lang.set("Language.Ingame.PartnerInvFull",		"Your partner have no empty space in his inventory.");
						lang.set("Language.Ingame.ItemSent",			"You have sent your partner %1$s %2$s.");
						lang.set("Language.Ingame.ItemReceived",		"Your partner have sent you %1$s %2$s.");
						lang.set("Language.Ingame.GiftsOnlyInSurvival",	"You can only gift items to your partner when you are in survival mode.");
				        lang.set("Language.Ingame.YouKissed",			"You have kissed your partner!");
				        lang.set("Language.Ingame.YouGotKissed",		"Your partner has kissed you!");
				        lang.set("Language.Ingame.TooFarToKiss",		"You are too far away to kiss your partner. (Max 2 Blocks)");
				        lang.set("Language.Ingame.KissWait",			"You have to wait %s seconds to kiss your partner again.");
						lang.set("Language.Description.Kiss",			"Kisses your partner.");
						lang.set("Language.Description.Gift",			"Gifts the item in your hand to your partner.");
						lang.set("Language.Console.MetricsOffline",		"Metrics offline.");
						lang.set("Language.Economy.GiftPaid",			"You have paid %1$s for gifting an item to your partner (%2$s left)");
					case 5:
						lang.set("Language.Economy.GiftPaid",			"You have paid %1$s for gifting an item to your partner (%2$s left)");
						lang.set("Language.Ingame.WorldNotAllowed",		"Your partner is in a world where tp is not allowed.");
						lang.set("Language.Ingame.Updated",				"Plugin updated, will be loaded on next restart/reload.");
						lang.set("Language.Ingame.NoUpdate",			"No plugin update available.");
						lang.set("Language.Description.Update",			"Checks if there is an update available and downloads it.");
						lang.set("Language.Description.Marry",			"Marry two Persons in a range of %s blocks.");
						lang.set("Language.Description.Divorce",		"Divorces two Persons in a range of %s blocks.");
					case 6:
					case 7:
					case 8:
						lang.set("Language.Console.UpdateUUIDs",		"Start updating database to UUIDs ...");
						lang.set("Language.Console.UpdatedUUIDs",		"Updated %s accounts to UUIDs.");
						lang.set("Language.Description.SelfMarry",		"Sends a marry request to a other player.");
						lang.set("Language.Priest.AlreadyOpenRequest",	"%s has already one not answered marry request.");
						lang.set("Language.Ingame.MarryConfirm",		"%s want to marry you. Accept the marriage with /marry accept or deny it with /marry deny");
						lang.set("Language.Ingame.HasMarried",			"You have married %s.");
						lang.set("Language.Ingame.BroadcastMarriage",	"%1$s has married %2$s.");
						lang.set("Language.Ingame.Divorced",			"You left %s. You are free now.");
						lang.set("Language.Ingame.DivorcedPlayer",		"%s has left you. You are free now.");
						lang.set("Language.Ingame.AlreadyMarried",		"You are already married!");
						lang.set("Language.Ingame.OtherAlreadyMarried",	"%s is already married.");
						lang.set("Language.Ingame.AlreadyOpenRequest",	"You have already one not answered marry request.");
						lang.set("Language.Ingame.MarryRequestSent", 	"Marriage request sent.");
					case 9:
						lang.set("Language.Description.ChatToggle",		"Toggles your chat to be private with your partner.");
						lang.set("Language.Ingame.TPMoved",				"Teleport canceled! You have moved!");
						lang.set("Language.Ingame.TPDontMove",			"Teleport in %s sec! Don't Move!");
					case 10:
						lang.set("Language.Ingame.NotYourself",			"You cannot marry yourself.");
					case 11:
						lang.set("Language.Ingame.ChatJoined",			"You have set your chat to the private marry chat.");
						lang.set("Language.Ingame.ChatLeft",			"You have set your chat to public chat.");
						lang.set("Language.Ingame.NaN",					"Entered value is not a number!");
						lang.set("Language.Ingame.NotInRange",			"Your partner is to far away.");
						lang.set("Language.Ingame.ListHLMP",			"Showing page %1$d/%2$d - /marry list <page>");
						lang.set("Language.Description.SelfDivorce",	"To leave your partner.");
						lang.set("Language.Description.Surname",		"Changes the surname of a couple.");
						lang.set("Language.Economy.NotEnoughPriestInfo","The players you are trying to marry don't have enough money.");
						lang.set("Language.Economy.DivNotEnoPriestI",	"The players you are trying to divorce don't have enough money.");
						lang.set("Language.Priest.DivPlayerOff",		"%s is now offline, divorce canceled.");
						lang.set("Language.Priest.DivPriestOff",		"Priest (%s) is now offline, divorce canceled.");
						lang.set("Language.Priest.PlayerCanceled",		"%s has denied the divorce.");
						lang.set("Language.Priest.DivorceCanceled",		"You have denied the divorce.");
						lang.set("Language.Priest.DivorceConfirm",		"Accept the divorce with /marry accept or deny it with /marry deny.");
						lang.set("Language.Priest.SurnameSet",			"Surname changed.");
					case 12:
						lang.set("Language.Ingame.BackpackOnlyInSurvival","You can only access your partners backpack when you are in survival mode.");
						lang.set("Language.Ingame.BackpackShareOn",		"You now share your backpack with your partner.");
						lang.set("Language.Ingame.BackpackShareOff",	"You now don't share your backpack with your partner.");
						lang.set("Language.Ingame.BackpackOpend",		"Your partner has opend your backpack.");
						lang.set("Language.Ingame.BackpackShareDenied",	"Your partner doesn't give you access to his backpack.");
						lang.set("Language.Description.Backpack",		"Opens the backpack of your partner.");
						lang.set("Language.Description.BackpackOn",		"Allows your partner to use your backpack.");
						lang.set("Language.Description.BackpackOff",	"Disallows your partner to use your backpack.");
					case 13:
						lang.set("Language.Ingame.CheckingForUpdates",	"Checking for updates ...");
					case 14:
						lang.set("Language.Ingame.BroadcastDivorce",	"%1$s has left %2$s.");
						lang.set("Language.Priest.BroadcastDivorce",	"%1$s has divorced %2$s from %3$s.");
						lang.set("Language.Priest.DivorceRequestSent",	"Divorce request sent.");
					case 15:
						lang.set("Language.Ingame.PlayerNoHome",		"The player doesn't have a home yet.");
					case 16:
						lang.set("Language.Ingame.HomeDeleted",			"Homepoint deleted.");
						lang.set("Language.Description.DelHome",		"Deletes your homepoint.");
						lang.set("Language.Description.TPHomeOther",	"Teleports you to a married players home.");
						lang.set("Language.Description.DelHomeOther",	"Deletes the home of a married player.");
					case 17:
						lang.set("Language.Ingame.TPUnsafe",			"Teleport cancled! You would not survive it!");
						lang.set("Language.Ingame.TPtoUnsafe",			"Your partner was about to teleport to you, but would not survive it!");
						break;
					default: plugin.log.warning("Language File Version newer than expected!"); return false;
				}
				lang.set("Version", LANG_VERSION);
				try
				{
					lang.save(file);
					plugin.log.info(get("Console.LangUpdated"));
				}
		  	  	catch (IOException e) 
		  	  	{
		  	  		e.printStackTrace();
		  	  	}
				return true;
			}
		}
		return false;
	}
}
